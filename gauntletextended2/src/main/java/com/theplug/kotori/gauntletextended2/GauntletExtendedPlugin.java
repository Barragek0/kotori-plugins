/*
 * Copyright (c) 2022, Kotori <https://github.com/OreoCupcakes/>
 * Copyright (c) 2020, dutta64 <https://github.com/dutta64>
 * Copyright (c) 2019, kThisIsCvpv <https://github.com/kThisIsCvpv>
 * Copyright (c) 2019, ganom <https://github.com/Ganom>
 * Copyright (c) 2019, kyle <https://github.com/xKylee>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.theplug.kotori.gauntletextended2;

import com.google.inject.Provides;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.*;
import net.runelite.client.plugins.PluginDependency;
import com.theplug.kotori.gauntletextended2.resource.ResourceManager;
import com.theplug.kotori.gauntletextended2.utils.GameObjectQuery;
import com.theplug.kotori.gauntletextended2.utils.GraphicIDPlus;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.theplug.kotori.gauntletextended2.entity.Demiboss;
import com.theplug.kotori.gauntletextended2.entity.Hunllef;
import com.theplug.kotori.gauntletextended2.entity.Missile;
import com.theplug.kotori.gauntletextended2.entity.Resource;
import com.theplug.kotori.gauntletextended2.entity.Tornado;
import com.theplug.kotori.gauntletextended2.overlay.Overlay;
import com.theplug.kotori.gauntletextended2.overlay.OverlayGauntlet;
import com.theplug.kotori.gauntletextended2.overlay.OverlayHunllef;
import com.theplug.kotori.gauntletextended2.overlay.OverlayPrayerBox;
import com.theplug.kotori.gauntletextended2.overlay.OverlayPrayerWidget;
import com.theplug.kotori.gauntletextended2.overlay.OverlayTimer;
import com.theplug.kotori.gauntletextended2.kotoriutils.KotoriUtils;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDependency(KotoriUtils.class)
@PluginDescriptor(
	name = "Gauntlet Extended Local",
	enabledByDefault = false,
	description = "All-in-one plugin for the Gauntlet.",
	tags = {"gauntlet", "corrupted", "ported", "cg", "prison"}
)
@Singleton
public class GauntletExtendedPlugin extends Plugin
{
	public static final int ONEHAND_SLASH_AXE_ANIMATION = 395;
	public static final int ONEHAND_CRUSH_PICKAXE_ANIMATION = 400;
	public static final int ONEHAND_CRUSH_AXE_ANIMATION = 401;
	public static final int UNARMED_PUNCH_ANIMATION = 422;
	public static final int UNARMED_KICK_ANIMATION = 423;
	public static final int BOW_ATTACK_ANIMATION = 426;
	public static final int ONEHAND_STAB_HALBERD_ANIMATION = 428;
	public static final int ONEHAND_SLASH_HALBERD_ANIMATION = 440;
	public static final int ONEHAND_SLASH_SWORD_ANIMATION = 390;
	public static final int ONEHAND_STAB_SWORD_ANIMATION = 386;
	public static final int HIGH_LEVEL_MAGIC_ATTACK = 1167;
	public static final int HUNLEFF_TORNADO = 8418;

	private static final Set<Integer> MELEE_ANIM_IDS = Set.of(
		ONEHAND_STAB_SWORD_ANIMATION, ONEHAND_SLASH_SWORD_ANIMATION,
		ONEHAND_SLASH_AXE_ANIMATION, ONEHAND_CRUSH_PICKAXE_ANIMATION,
		ONEHAND_CRUSH_AXE_ANIMATION, UNARMED_PUNCH_ANIMATION,
		UNARMED_KICK_ANIMATION, ONEHAND_STAB_HALBERD_ANIMATION,
		ONEHAND_SLASH_HALBERD_ANIMATION
	);

	private static final Set<Integer> ATTACK_ANIM_IDS = new HashSet<>();

	static
	{
		ATTACK_ANIM_IDS.addAll(MELEE_ANIM_IDS);
		ATTACK_ANIM_IDS.add(BOW_ATTACK_ANIMATION);
		ATTACK_ANIM_IDS.add(HIGH_LEVEL_MAGIC_ATTACK);
	}

	private static final Set<Integer> PROJECTILE_MAGIC_IDS = Set.of(
		GraphicIDPlus.HUNLLEF_MAGE_ATTACK, GraphicIDPlus.HUNLLEF_CORRUPTED_MAGE_ATTACK
	);

	private static final Set<Integer> PROJECTILE_RANGE_IDS = Set.of(
			GraphicIDPlus.HUNLLEF_RANGE_ATTACK, GraphicIDPlus.HUNLLEF_CORRUPTED_RANGE_ATTACK
	);

	private static final Set<Integer> PROJECTILE_PRAYER_IDS = Set.of(
			GraphicIDPlus.HUNLLEF_PRAYER_ATTACK, GraphicIDPlus.HUNLLEF_CORRUPTED_PRAYER_ATTACK
	);

	private static final Set<Integer> PROJECTILE_IDS = new HashSet<>();

	static
	{
		PROJECTILE_IDS.addAll(PROJECTILE_MAGIC_IDS);
		PROJECTILE_IDS.addAll(PROJECTILE_RANGE_IDS);
		PROJECTILE_IDS.addAll(PROJECTILE_PRAYER_IDS);
	}

	private static final Set<Integer> HUNLLEF_IDS = Set.of(
		NpcID.CRYSTALLINE_HUNLLEF, NpcID.CRYSTALLINE_HUNLLEF_9022,
		NpcID.CRYSTALLINE_HUNLLEF_9023, NpcID.CRYSTALLINE_HUNLLEF_9024,
		NpcID.CORRUPTED_HUNLLEF, NpcID.CORRUPTED_HUNLLEF_9036,
		NpcID.CORRUPTED_HUNLLEF_9037, NpcID.CORRUPTED_HUNLLEF_9038
	);

	private static final Set<Integer> TORNADO_IDS = Set.of(NullNpcID.NULL_9025, NullNpcID.NULL_9039);

	private static final Set<Integer> DEMIBOSS_IDS = Set.of(
		NpcID.CRYSTALLINE_BEAR, NpcID.CORRUPTED_BEAR,
		NpcID.CRYSTALLINE_DARK_BEAST, NpcID.CORRUPTED_DARK_BEAST,
		NpcID.CRYSTALLINE_DRAGON, NpcID.CORRUPTED_DRAGON
	);

	private static final Set<Integer> STRONG_NPC_IDS = Set.of(
		NpcID.CRYSTALLINE_SCORPION, NpcID.CORRUPTED_SCORPION,
		NpcID.CRYSTALLINE_UNICORN, NpcID.CORRUPTED_UNICORN,
		NpcID.CRYSTALLINE_WOLF, NpcID.CORRUPTED_WOLF
	);

	private static final Set<Integer> WEAK_NPC_IDS = Set.of(
		NpcID.CRYSTALLINE_BAT, NpcID.CORRUPTED_BAT,
		NpcID.CRYSTALLINE_RAT, NpcID.CORRUPTED_RAT,
		NpcID.CRYSTALLINE_SPIDER, NpcID.CORRUPTED_SPIDER
	);

	private static final Set<Integer> RESOURCE_IDS = Set.of(
		ObjectID.CRYSTAL_DEPOSIT, ObjectID.CORRUPT_DEPOSIT,
		ObjectID.PHREN_ROOTS, ObjectID.CORRUPT_PHREN_ROOTS,
		ObjectID.FISHING_SPOT_36068, ObjectID.CORRUPT_FISHING_SPOT,
		ObjectID.GRYM_ROOT, ObjectID.CORRUPT_GRYM_ROOT,
		ObjectID.LINUM_TIRINUM, ObjectID.CORRUPT_LINUM_TIRINUM
	);

	private static final Set<Integer> UTILITY_IDS = Set.of(
		ObjectID.SINGING_BOWL_35966, ObjectID.SINGING_BOWL_36063,
		ObjectID.RANGE_35980, ObjectID.RANGE_36077,
		ObjectID.WATER_PUMP_35981, ObjectID.WATER_PUMP_36078
	);

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private GauntletExtendedConfig config;

	@Inject
	private ResourceManager resourceManager;

	@Inject
	private SkillIconManager skillIconManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private OverlayTimer overlayTimer;

	@Inject
	private OverlayGauntlet overlayGauntlet;

	@Inject
	private OverlayHunllef overlayHunllef;

	@Inject
	private OverlayPrayerWidget overlayPrayerWidget;

	@Inject
	private OverlayPrayerBox overlayPrayerBox;

	private Set<Overlay> overlays;

	@Getter
	private final Set<Resource> resources = new HashSet<>();

	@Getter
	private final Set<GameObject> utilities = new HashSet<>();

	@Getter
	private final Set<Tornado> tornadoes = new HashSet<>();

	@Getter
	private final Set<Demiboss> demibosses = new HashSet<>();

	@Getter
	private final Set<NPC> strongNpcs = new HashSet<>();

	@Getter
	private final Set<NPC> weakNpcs = new HashSet<>();

	private final List<Set<?>> entitySets = Arrays.asList(resources, utilities, tornadoes, demibosses, strongNpcs, weakNpcs);

	@Getter
	private Missile missile;

	@Getter
	private Hunllef hunllef;

	@Inject
	private KotoriUtils kotoriUtils;

	// 0 = inventory slot, 1 = weapon id
	private int[] meleeWeaponItemInfo = new int[2];
	private int[] rangedWeaponItemInfo = new int[2];
	private int[] mageWeaponItemInfo = new int[2];
	// 0 = melee, 1 = ranged, 2 = mage
	private String[] weaponNames = new String[3];

	@Getter
	@Setter
	private boolean wrongAttackStyle;

	@Getter
	@Setter
	private boolean switchWeapon;

	private boolean inGauntlet;
	private boolean inHunllef;

	@Provides
	GauntletExtendedConfig getConfig(final ConfigManager configManager)
	{
		return configManager.getConfig(GauntletExtendedConfig.class);
	}

	@Override
	protected void startUp()
	{
		if (overlays == null)
		{
			overlays = Set.of(overlayTimer, overlayGauntlet, overlayHunllef, overlayPrayerWidget, overlayPrayerBox);
		}

		if (client.getGameState() == GameState.LOGGED_IN)
		{
			clientThread.invoke(this::pluginEnabled);
		}
	}

	@Override
	protected void shutDown()
	{
		overlays.forEach(o -> overlayManager.remove(o));

		inGauntlet = false;
		inHunllef = false;

		hunllef = null;
		missile = null;
		wrongAttackStyle = false;
		switchWeapon = false;

		overlayTimer.reset();
		resourceManager.reset();

		entitySets.forEach(Set::clear);
	}

	@Subscribe
	private void onConfigChanged(final ConfigChanged event)
	{
		if (!event.getGroup().equals("gauntlet"))
		{
			return;
		}

		switch (event.getKey())
		{
			case "resourceIconSize":
				if (!resources.isEmpty())
				{
					resources.forEach(r -> r.setIconSize(config.resourceIconSize()));
				}
				break;
			case "resourceTracker":
				if (inGauntlet && !inHunllef)
				{
					resourceManager.reset();
					resourceManager.init();
				}
				break;
			case "projectileIconSize":
				if (missile != null)
				{
					missile.setIconSize(config.projectileIconSize());
				}
				break;
			case "hunllefAttackStyleIconSize":
				if (hunllef != null)
				{
					hunllef.setIconSize(config.hunllefAttackStyleIconSize());
				}
				break;
			case "mirrorMode":
				overlays.forEach(overlay -> {
					overlay.determineLayer();

					if (overlayManager.anyMatch(o -> o == overlay))
					{
						overlayManager.remove(overlay);
						overlayManager.add(overlay);
					}
				});
				break;
			default:
				break;
		}
	}

	@Subscribe
	private void onVarbitChanged(final VarbitChanged event)
	{
		if (isHunllefVarbitSet())
		{
			if (!inHunllef)
			{
				initHunllef();
			}
		}
		else if (isGauntletVarbitSet())
		{
			if (!inGauntlet)
			{
				initGauntlet();
			}
		}
		else
		{
			if (inGauntlet || inHunllef)
			{
				shutDown();
			}
		}
	}

	@Subscribe
	private void onGameTick(final GameTick event)
	{
		if (hunllef == null)
		{
			return;
		}

		hunllef.decrementTicksUntilNextAttack();

		if (missile != null && missile.getProjectile().getRemainingCycles() <= 0)
		{
			missile = null;
		}

		if (!tornadoes.isEmpty())
		{
			tornadoes.forEach(Tornado::updateTimeLeft);
		}

		if (config.autoProtectionPrayers())
		{
			prayDefensivelyForHunllef();
		}

		if (config.autoEquipCorrectWeapon())
		{
			equipWeapon();
		}

		if (config.autoOffensivePrayers())
		{
			prayOffensivelyForHunllef();
		}
	}

	@Subscribe
	private void onGameStateChanged(final GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case LOADING:
				resources.clear();
				utilities.clear();
				break;
			case LOGIN_SCREEN:
			case HOPPING:
				shutDown();
				break;
		}
	}

	@Subscribe
	private void onWidgetLoaded(final WidgetLoaded event)
	{
		if (event.getGroupId() == WidgetID.GAUNTLET_TIMER_GROUP_ID)
		{
			overlayTimer.setGauntletStart();
			resourceManager.init();
		}
	}

	@Subscribe
	private void onGameObjectSpawned(final GameObjectSpawned event)
	{
		final GameObject gameObject = event.getGameObject();

		final int id = gameObject.getId();

		if (RESOURCE_IDS.contains(id))
		{
			resources.add(new Resource(gameObject, skillIconManager, config.resourceIconSize()));
		}
		else if (UTILITY_IDS.contains(id))
		{
			utilities.add(gameObject);
		}
	}

	@Subscribe
	private void onGameObjectDespawned(final GameObjectDespawned event)
	{
		final GameObject gameObject = event.getGameObject();

		final int id = gameObject.getId();

		if (RESOURCE_IDS.contains(gameObject.getId()))
		{
			resources.removeIf(o -> o.getGameObject() == gameObject);
		}
		else if (UTILITY_IDS.contains(id))
		{
			utilities.remove(gameObject);
		}
	}

	@Subscribe
	private void onNpcSpawned(final NpcSpawned event)
	{
		final NPC npc = event.getNpc();

		final int id = npc.getId();

		if (HUNLLEF_IDS.contains(id))
		{
			hunllef = new Hunllef(npc, skillIconManager, config.hunllefAttackStyleIconSize());
		}
		else if (TORNADO_IDS.contains(id))
		{
				if (tornadoes.isEmpty())
					tornadoes.add(new Tornado(npc));
				else {
					for (Tornado tornado : tornadoes) {
						if (tornado.getNpc() == npc) {
							return;
						}
					}
					tornadoes.add(new Tornado(npc));
				}
		}
		else if (DEMIBOSS_IDS.contains(id))
		{
			demibosses.add(new Demiboss(npc));
		}
		else if (STRONG_NPC_IDS.contains(id))
		{
			strongNpcs.add(npc);
		}
		else if (WEAK_NPC_IDS.contains(id))
		{
			weakNpcs.add(npc);
		}
	}

	@Subscribe
	private void onNpcDespawned(final NpcDespawned event)
	{
		final NPC npc = event.getNpc();

		final int id = npc.getId();

		if (HUNLLEF_IDS.contains(id))
		{
			hunllef = null;
		}
		else if (TORNADO_IDS.contains(id))
		{
			if (tornadoes.isEmpty())
				return;
			else {
				for (Tornado tornado : tornadoes) {
					if (tornado.getTimeLeft() <= 0) {
						tornadoes.remove(tornado);
					}
				}
			}
		}
		else if (DEMIBOSS_IDS.contains(id))
		{
			demibosses.removeIf(d -> d.getNpc() == npc);
		}
		else if (STRONG_NPC_IDS.contains(id))
		{
			strongNpcs.remove(npc);
		}
		else if (WEAK_NPC_IDS.contains(id))
		{
			weakNpcs.remove(npc);
		}
	}

	@Subscribe
	private void onProjectileMoved(final ProjectileMoved event)
	{
		if (hunllef == null)
		{
			return;
		}

		final Projectile projectile = event.getProjectile();

		final int id = projectile.getId();

		if (!PROJECTILE_IDS.contains(id))
		{
			return;
		}

		/*
			If there is no stored projectile currently (missile) then store the event projectile, update Hunllef's attack counter,
			and play the Prayer attack sound effect if needed. Otherwise if the projectile has already been stored, do nothing.
			missile will get nulled out in the onGameTick function where it will check if the stored projectile's remainingCycles
			is <= 0.
		 */
		if (missile == null)
		{
			missile = new Missile(projectile, skillIconManager, config.projectileIconSize());
		}
		else
		{
			return;
		}

		hunllef.updateAttackCount();

		if (PROJECTILE_PRAYER_IDS.contains(id) && config.hunllefPrayerAudio())
		{
			client.playSoundEffect(SoundEffectID.MAGIC_SPLASH_BOING);
		}
	}

	@Subscribe
	private void onChatMessage(final ChatMessage event)
	{
		final ChatMessageType type = event.getType();

		if (type == ChatMessageType.SPAM || type == ChatMessageType.GAMEMESSAGE)
		{
			resourceManager.parseChatMessage(event.getMessage());
		}
	}

	@Subscribe
	private void onActorDeath(final ActorDeath event)
	{
		if (event.getActor() != client.getLocalPlayer())
		{
			return;
		}

		overlayTimer.onPlayerDeath();
	}

	@Subscribe
	private void onAnimationChanged(final AnimationChanged event)
	{
		if (!isHunllefVarbitSet() || hunllef == null)
		{
			return;
		}

		final Actor actor = event.getActor();

		final int animationId = actor.getAnimation();

		if (actor instanceof Player)
		{
			if (!ATTACK_ANIM_IDS.contains(animationId))
			{
				return;
			}

			final boolean validAttack = isAttackAnimationValid(animationId);

			if (validAttack)
			{
				wrongAttackStyle = false;
				hunllef.updatePlayerAttackCount();

				if (hunllef.getPlayerAttackCount() == 1)
				{
					switchWeapon = true;
				}
			}
			else
			{
				wrongAttackStyle = true;
			}
		}
		else if (actor instanceof NPC)
		{
			if (animationId == HUNLEFF_TORNADO)
			{
				hunllef.updateAttackCount();
			}
		}
	}

	@Subscribe
	private void onItemContainerChanged(final ItemContainerChanged event)
	{
		if (!isHunllefVarbitSet() || hunllef == null)
		{
			return;
		}

		if (event.getContainerId() == InventoryID.INVENTORY.getId())
		{
			setWeaponsForHunllef();
		}
	}

	private boolean isAttackAnimationValid(final int animationId)
	{
		HeadIcon headIcon = getHunllefHeadIcon();
		/*
			Old API code
			final HeadIcon headIcon = hunllef.getNpc().getComposition().getOverheadIcon();
		*/
		if (headIcon == null)
		{
			return true;
		}

		switch (headIcon)
		{
			case MELEE:
				if (MELEE_ANIM_IDS.contains(animationId))
				{
					return false;
				}
				break;
			case RANGED:
				if (animationId == BOW_ATTACK_ANIMATION)
				{
					return false;
				}
				break;
			case MAGIC:
				if (animationId == HIGH_LEVEL_MAGIC_ATTACK)
				{
					return false;
				}
				break;
		}

		return true;
	}

	private HeadIcon getHunllefHeadIcon()
	{
		NPCComposition hunllefComposition = hunllef.getNpc().getComposition();
		System.out.println("NPCComposition Name: " + hunllefComposition.getName() + " NPC ID: " + hunllefComposition.getId());
		return kotoriUtils.getNpcsLibrary().getNPCHeadIcon(hunllefComposition);
	}

	private void prayDefensivelyForHunllef()
	{
		if (!isHunllefVarbitSet() || hunllef == null)
		{
			return;
		}

		Prayer protectionPrayer = hunllef.getAttackPhase().getPrayer();

		kotoriUtils.getInvokesLibrary().invokePrayer(protectionPrayer);
	}

	private void prayOffensivelyForHunllef()
	{
		if (!isHunllefVarbitSet() || hunllef == null)
		{
			return;
		}

		String equippedWeaponType = getEquippedWeaponType();

		switch (equippedWeaponType)
		{
			case "RANGED":
				kotoriUtils.getInvokesLibrary().invokePrayer(config.autoRangedPrayerDropDown().getPrayer());
				break;
			case "MAGIC":
				kotoriUtils.getInvokesLibrary().invokePrayer(config.autoMagePrayerDropDown().getPrayer());
				break;
			case "MELEE":
			case "OTHER":
				kotoriUtils.getInvokesLibrary().invokePrayer(config.autoMeleePrayerDropDown().getPrayer());
				break;
			default:
				break;
		}
	}

	private int getEquippedWeaponID()
	{
		Item equippedWeapon = client.getItemContainer(InventoryID.EQUIPMENT).getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());

		if (equippedWeapon == null)
		{
			return -1;
		}
		else
		{
			return equippedWeapon.getId();
		}
	}
	private String getEquippedWeaponType()
	{
		int equippedWeaponID = getEquippedWeaponID();
		String weaponType;

		switch (equippedWeaponID) {
			case 23857:
			case 23856:
			case 23855:
			case 23903:
			case 23902:
			case 23901:
				weaponType = "RANGED";
				break;
			case 23854:
			case 23853:
			case 23852:
			case 23900:
			case 23899:
			case 23898:
				weaponType = "MAGIC";
				break;
			case 23851:
			case 23850:
			case 23849:
			case 23820:
			case 23897:
			case 23896:
			case 23895:
			case 23861:
				weaponType = "MELEE";
				break;
			default:
				weaponType = "OTHER";
				break;
		}
		return weaponType;
	}

	private void equipWeapon()
	{
		if (!isHunllefVarbitSet() || hunllef == null)
		{
			return;
		}

		HeadIcon hunllefHeadIcon = getHunllefHeadIcon();
		ItemContainer itemContainer = client.getItemContainer(InventoryID.INVENTORY);

		if ((getEquippedWeaponType() != hunllefHeadIcon.name()) && getEquippedWeaponType() != "OTHER")
		{
			return;
		}

		switch (hunllefHeadIcon) {
			case MAGIC:
				if (itemContainer.contains(rangedWeaponItemInfo[1]))
				{
					kotoriUtils.getInvokesLibrary().invokeWieldWeapon(rangedWeaponItemInfo[0],rangedWeaponItemInfo[1],weaponNames[1]);
				}
				else if (itemContainer.contains(meleeWeaponItemInfo[1]))
				{
					kotoriUtils.getInvokesLibrary().invokeWieldWeapon(meleeWeaponItemInfo[0], meleeWeaponItemInfo[1], weaponNames[0]);
				}
				break;
			case RANGED:
				if (itemContainer.contains(mageWeaponItemInfo[1]))
				{
					kotoriUtils.getInvokesLibrary().invokeWieldWeapon(mageWeaponItemInfo[0],mageWeaponItemInfo[1],weaponNames[2]);
				}
				else if (itemContainer.contains(meleeWeaponItemInfo[1]))
				{
					kotoriUtils.getInvokesLibrary().invokeWieldWeapon(meleeWeaponItemInfo[0], meleeWeaponItemInfo[1], weaponNames[0]);
				}
				break;
			case MELEE:
				if (itemContainer.contains(mageWeaponItemInfo[1]))
				{
					kotoriUtils.getInvokesLibrary().invokeWieldWeapon(mageWeaponItemInfo[0],mageWeaponItemInfo[1],weaponNames[2]);
				}
				else if (itemContainer.contains(rangedWeaponItemInfo[1]))
				{
					kotoriUtils.getInvokesLibrary().invokeWieldWeapon(rangedWeaponItemInfo[0],rangedWeaponItemInfo[1],weaponNames[1]);
				}
				break;
			default:
				break;
		}
	}

	private void setWeaponsForHunllef()
	{
		// Index 0,1,2 are Corrupted Gauntlet weapons. Index 3,4,5 are Crystalline Gauntlet Weapons
		final int[] rangedWeapons = {23857,23856,23855,23903,23902,23901};
		final int[] mageWeapons = {23854,23853,23852,23900,23899,23898};
		final int[] meleeWeapons = {23851,23850,23849,23820,23897,23896,23895,23861};

		Item[] inventory = client.getItemContainer(InventoryID.INVENTORY).getItems();

		rangedLoop:
			for (int rangedIndex = 0; rangedIndex < rangedWeapons.length; rangedIndex++)
			{
				for (int invenIndex = 0; invenIndex < inventory.length; invenIndex++)
				{
					if (inventory[invenIndex].getId() == rangedWeapons[rangedIndex])
					{
						rangedWeaponItemInfo[0] = invenIndex;
						rangedWeaponItemInfo[1] = rangedWeapons[rangedIndex];
						weaponNames[1] = client.getItemDefinition(rangedWeapons[rangedIndex]).getMembersName();
						break rangedLoop;
					}
				}
			}

		mageLoop:
			for (int mageIndex = 0; mageIndex < mageWeapons.length; mageIndex++)
			{
				for (int invenIndex = 0; invenIndex < inventory.length; invenIndex++)
				{
					if (inventory[invenIndex].getId() == mageWeapons[mageIndex])
					{
						mageWeaponItemInfo[0] = invenIndex;
						mageWeaponItemInfo[1] = mageWeapons[mageIndex];
						weaponNames[2] = client.getItemDefinition(mageWeapons[mageIndex]).getMembersName();
						break mageLoop;
					}
				}
			}

		meleeLoop:
			for (int meleeIndex = 0; meleeIndex < meleeWeapons.length; meleeIndex++)
			{
				for (int invenIndex = 0; invenIndex < inventory.length; invenIndex++)
				{
					if (inventory[invenIndex].getId() == meleeWeapons[meleeIndex])
					{
						meleeWeaponItemInfo[0] = invenIndex;
						meleeWeaponItemInfo[1] = meleeWeapons[meleeIndex];
						weaponNames[0] = client.getItemDefinition(meleeWeapons[meleeIndex]).getMembersName();
						break meleeLoop;
					}
				}
			}
	}

	private void pluginEnabled()
	{
		if (isGauntletVarbitSet())
		{
			overlayTimer.setGauntletStart();
			resourceManager.init();
			addSpawnedEntities();
			initGauntlet();
		}

		if (isHunllefVarbitSet())
		{
			initHunllef();
		}
	}

	private void addSpawnedEntities()
	{
		for (final GameObject gameObject : new GameObjectQuery().getGameObjectQuery(client))
		{
			GameObjectSpawned gameObjectSpawned = new GameObjectSpawned();
			gameObjectSpawned.setTile(null);
			gameObjectSpawned.setGameObject(gameObject);
			onGameObjectSpawned(gameObjectSpawned);
		}

		for (final NPC npc : client.getNpcs())
		{
			onNpcSpawned(new NpcSpawned(npc));
		}
	}

	private void initGauntlet()
	{
		inGauntlet = true;

		overlayManager.add(overlayTimer);
		overlayManager.add(overlayGauntlet);
	}

	private void initHunllef()
	{
		inHunllef = true;

		overlayTimer.setHunllefStart();
		resourceManager.reset();

		overlayManager.remove(overlayGauntlet);
		overlayManager.add(overlayHunllef);
		overlayManager.add(overlayPrayerWidget);
		overlayManager.add(overlayPrayerBox);

		rangedWeaponItemInfo = new int[2];
		mageWeaponItemInfo = new int[2];
		meleeWeaponItemInfo = new int[2];
		setWeaponsForHunllef();
	}

	private boolean isGauntletVarbitSet()
	{
		return client.getVarbitValue(9178) == 1;
	}

	private boolean isHunllefVarbitSet()
	{
		return client.getVarbitValue(9177) == 1;
	}
}
