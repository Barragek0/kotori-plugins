/*
 * Copyright (c) 2022, Kotori <https://github.com/OreoCupcakes/>
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
 *
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

package com.theplug.kotori.alchemicalhydrarlpl.utils;

import net.runelite.api.Prayer;
import net.runelite.api.Varbits;

public enum PrayerExtended {
    /**
     * Thick Skin (Level 1, Defence).
     */
    THICK_SKIN(Varbits.PRAYER_THICK_SKIN, 5.0, PrayerWidgetInfo.PRAYER_THICK_SKIN),
    /**
     * Burst of Strength (Level 4, Strength).
     */
    BURST_OF_STRENGTH(Varbits.PRAYER_BURST_OF_STRENGTH, 5.0, PrayerWidgetInfo.PRAYER_BURST_OF_STRENGTH),
    /**
     * Clarity of Thought (Level 7, Attack).
     */
    CLARITY_OF_THOUGHT(Varbits.PRAYER_CLARITY_OF_THOUGHT, 5.0, PrayerWidgetInfo.PRAYER_CLARITY_OF_THOUGHT),
    /**
     * Sharp Eye (Level 8, Ranging).
     */
    SHARP_EYE(Varbits.PRAYER_SHARP_EYE, 5.0, PrayerWidgetInfo.PRAYER_SHARP_EYE),
    /**
     * Mystic Will (Level 9, Magic).
     */
    MYSTIC_WILL(Varbits.PRAYER_MYSTIC_WILL, 5.0, PrayerWidgetInfo.PRAYER_MYSTIC_WILL),
    /**
     * Rock Skin (Level 10, Defence).
     */
    ROCK_SKIN(Varbits.PRAYER_ROCK_SKIN, 10.0, PrayerWidgetInfo.PRAYER_ROCK_SKIN),
    /**
     * Superhuman Strength (Level 13, Strength).
     */
    SUPERHUMAN_STRENGTH(Varbits.PRAYER_SUPERHUMAN_STRENGTH, 10.0, PrayerWidgetInfo.PRAYER_SUPERHUMAN_STRENGTH),
    /**
     * Improved Reflexes (Level 16, Attack).
     */
    IMPROVED_REFLEXES(Varbits.PRAYER_IMPROVED_REFLEXES, 10.0, PrayerWidgetInfo.PRAYER_IMPROVED_REFLEXES),
    /**
     * Rapid Restore (Level 19, Stats).
     */
    RAPID_RESTORE(Varbits.PRAYER_RAPID_RESTORE, 60.0 / 36.0, PrayerWidgetInfo.PRAYER_RAPID_RESTORE),
    /**
     * Rapid Heal (Level 22, Hitpoints).
     */
    RAPID_HEAL(Varbits.PRAYER_RAPID_HEAL, 60.0 / 18, PrayerWidgetInfo.PRAYER_RAPID_HEAL),
    /**
     * Protect Item (Level 25).
     */
    PROTECT_ITEM(Varbits.PRAYER_PROTECT_ITEM, 60.0 / 18, PrayerWidgetInfo.PRAYER_PROTECT_ITEM),
    /**
     * Hawk Eye (Level 26, Ranging).
     */
    HAWK_EYE(Varbits.PRAYER_HAWK_EYE, 10.0, PrayerWidgetInfo.PRAYER_HAWK_EYE),
    /**
     * Mystic Lore (Level 27, Magic).
     */
    MYSTIC_LORE(Varbits.PRAYER_MYSTIC_LORE, 10.0, PrayerWidgetInfo.PRAYER_MYSTIC_LORE),
    /**
     * Steel Skin (Level 28, Defence).
     */
    STEEL_SKIN(Varbits.PRAYER_STEEL_SKIN, 20.0, PrayerWidgetInfo.PRAYER_STEEL_SKIN),
    /**
     * Ultimate Strength (Level 31, Strength).
     */
    ULTIMATE_STRENGTH(Varbits.PRAYER_ULTIMATE_STRENGTH, 20.0, PrayerWidgetInfo.PRAYER_ULTIMATE_STRENGTH),
    /**
     * Incredible Reflexes (Level 34, Attack).
     */
    INCREDIBLE_REFLEXES(Varbits.PRAYER_INCREDIBLE_REFLEXES, 20.0, PrayerWidgetInfo.PRAYER_INCREDIBLE_REFLEXES),
    /**
     * Protect from Magic (Level 37).
     */
    PROTECT_FROM_MAGIC(Varbits.PRAYER_PROTECT_FROM_MAGIC, 20.0, PrayerWidgetInfo.PRAYER_PROTECT_FROM_MAGIC),
    /**
     * Protect from Missiles (Level 40).
     */
    PROTECT_FROM_MISSILES(Varbits.PRAYER_PROTECT_FROM_MISSILES, 20.0, PrayerWidgetInfo.PRAYER_PROTECT_FROM_MISSILES),
    /**
     * Protect from Melee (Level 43).
     */
    PROTECT_FROM_MELEE(Varbits.PRAYER_PROTECT_FROM_MELEE, 20.0, PrayerWidgetInfo.PRAYER_PROTECT_FROM_MELEE),
    /**
     * Eagle Eye (Level 44, Ranging).
     */
    EAGLE_EYE(Varbits.PRAYER_EAGLE_EYE, 20.0, PrayerWidgetInfo.PRAYER_EAGLE_EYE),
    /**
     * Mystic Might (Level 45, Magic).
     */
    MYSTIC_MIGHT(Varbits.PRAYER_MYSTIC_MIGHT, 20.0, PrayerWidgetInfo.PRAYER_MYSTIC_MIGHT),
    /**
     * Retribution (Level 46).
     */
    RETRIBUTION(Varbits.PRAYER_RETRIBUTION, 5.0, PrayerWidgetInfo.PRAYER_RETRIBUTION),
    /**
     * Redemption (Level 49).
     */
    REDEMPTION(Varbits.PRAYER_REDEMPTION, 10.0, PrayerWidgetInfo.PRAYER_REDEMPTION),
    /**
     * Smite (Level 52).
     */
    SMITE(Varbits.PRAYER_SMITE, 30.0, PrayerWidgetInfo.PRAYER_SMITE),
    /**
     * Chivalry (Level 60, Defence/Strength/Attack).
     */
    CHIVALRY(Varbits.PRAYER_CHIVALRY, 40.0, PrayerWidgetInfo.PRAYER_CHIVALRY),
    /**
     * Piety (Level 70, Defence/Strength/Attack).
     */
    PIETY(Varbits.PRAYER_PIETY, 40.0, PrayerWidgetInfo.PRAYER_PIETY),
    /**
     * Preserve (Level 55).
     */
    PRESERVE(Varbits.PRAYER_PRESERVE, 60.0 / 18, PrayerWidgetInfo.PRAYER_PRESERVE),
    /**
     * Rigour (Level 74, Ranging/Damage/Defence).
     */
    RIGOUR(Varbits.PRAYER_RIGOUR, 40.0, PrayerWidgetInfo.PRAYER_RIGOUR),
    /**
     * Augury (Level 77, Magic/Magic Def./Defence).
     */
    AUGURY(Varbits.PRAYER_AUGURY, 40.0, PrayerWidgetInfo.PRAYER_AUGURY),
    /**
     * Rejuvenation (Level 60, Hitpoints).
     */
    REJUVENATION(14840, 60.0 / 9, PrayerWidgetInfo.PRAYER_REJUVENATION),
    /**
     * Ancient Strength (Level 61, Strength/Attack).
     */
    ANCIENT_STRENGTH(14829, 40.0, PrayerWidgetInfo.PRAYER_ANCIENT_STRENGTH),
    /**
     * Ancient Sight (Level 62, Ranging/Damage).
     */
    ANCIENT_SIGHT(14830, 40.0, PrayerWidgetInfo.PRAYER_ANCIENT_SIGHT),
    /**
     * Ancient Will (level 63, Magic).
     */
    ANCIENT_WILL(14831, 40.0, PrayerWidgetInfo.PRAYER_ANCIENT_WILL),
    /**
     * Ruinous Grace (Level 64, Run Energy).
     */
    RUINOUS_GRACE(14841, 60.0 / 36, PrayerWidgetInfo.PRAYER_RUINOUS_GRACE),
    /**
     * Deflect Magic (Level 65).
     */
    DEFLECT_MAGIC(14838, 30.0, PrayerWidgetInfo.PRAYER_DEFLECT_MAGIC),
    /**
     * Deflect Ranged (Level 68).
     */
    DEFLECT_RANGED(14837, 30.0, PrayerWidgetInfo.PRAYER_DEFLECT_RANGED),
    /**
     * Deflect Melee (Level 70).
     */
    DEFLECT_MELEE(14836, 30.0, PrayerWidgetInfo.PRAYER_DEFLECT_MELEE),
    /**
     * Trinitas (Level 71, Attack/Strength/Ranging/Damage/Magic).
     */
    TRINITAS(14832, 50.0, PrayerWidgetInfo.PRAYER_TRINITAS),
    /**
     * Berserker (Level 73, Stats).
     */
    BERSERKER(14844, 60.0 / 18, PrayerWidgetInfo.PRAYER_BERSERKER),
    /**
     * Purge (Level 74).
     */
    PURGE(14839, 60.0 / 1.63, PrayerWidgetInfo.PRAYER_PURGE),
    /**
     * Metabolise (Level 75).
     */
    METABOLISE(14843, 20.0, PrayerWidgetInfo.PRAYER_METABOLISE),
    /**
     * Decimate (Level 76, Attack/Strength).
     */
    DECIMATE(14833, 50.0, PrayerWidgetInfo.PRAYER_DECIMATE),
    /**
     * Annihilate (Level 78, Ranging/Damage).
     */
    ANNIHILATE(14834, 50.0, PrayerWidgetInfo.PRAYER_ANNIHILATE),
    /**
     * Vaporise (Level 80, Magic/Damage).
     */
    VAPORISE(14835, 50.0, PrayerWidgetInfo.PRAYER_VAPORISE),
    /**
     * Rebuke (Level 81).
     */
    REBUKE(14850, 30.0, PrayerWidgetInfo.PRAYER_REBUKE),
    /**
     * Fumus' Vow (Level 82).
     */
    FUMUS_VOW(14845, 20.0, PrayerWidgetInfo.PRAYER_FUMUS_VOW),
    /**
     * Umbra's Vow (Level 84).
     */
    UMBRAS_VOW(14847, 20.0, PrayerWidgetInfo.PRAYER_UMBRAS_VOW),
    /**
     * Cruor's Vow (Level 86).
     */
    CRUORS_VOW(14846, 20.0, PrayerWidgetInfo.PRAYER_CRUORS_VOW),
    /**
     * Glacie's Vow (Level 88).
     */
    GLACIES_VOW(14848, 20.0, PrayerWidgetInfo.PRAYER_GLACIES_VOW),
    /**
     * Wrath (Level 89).
     */
    WRATH(14842, 5.0, PrayerWidgetInfo.PRAYER_WRATH),
    /**
     * Vindication (Level 90).
     */
    VINDICATION(14851, 15.0, PrayerWidgetInfo.PRAYER_VINDICATION),
    /**
     * Gambit (Level 92).
     */
    GAMBIT(14849, 40.0, PrayerWidgetInfo.PRAYER_GAMBIT);

    private final int varbit;
    private final double drainRate;
    private final PrayerWidgetInfo prayerWidgetInfo;

    PrayerExtended(int varbit, double drainRate, PrayerWidgetInfo prayerWidgetInfo)
    {
        this.varbit = varbit;
        this.drainRate = drainRate;
        this.prayerWidgetInfo = prayerWidgetInfo;
    }

    /**
     * Gets the varbit that stores whether the prayer is active or not.
     *
     * @return the prayer active varbit
     */
    public int getVarbit()
    {
        return varbit;
    }

    /**
     * Gets the prayer drain rate (measured in pray points/minute)
     *
     * @return the prayer drain rate
     */
    public double getDrainRate()
    {
        return drainRate;
    }

    public PrayerWidgetInfo getPrayerWidgetInfo()
    {
        return prayerWidgetInfo;
    }

    public Prayer getPrayer() {
        String prayerName = prayerWidgetInfo.name();
        //Modify WidgetInfo name to remove the PRAYER_ part from the start
        prayerName = prayerName.substring(7,prayerName.length());
        return Prayer.valueOf(prayerName);
    }

    public static int getPrayerWidgetId(Prayer prayer)
    {
        return PrayerExtended.valueOf(prayer.name()).getPrayerWidgetInfo().getId();
    }
}
