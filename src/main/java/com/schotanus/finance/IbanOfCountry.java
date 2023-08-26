
/*
 * Copyright (C) 2014 LeanApps b.v.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.schotanus.finance;

import com.schotanus.util.Iso3166Country;


/**
 * Enumeration of IBAN used in different countries.<br>
 * The enumerated constants can be used to verify whether a country supports IBAN.
 * @see <a href="http://www.nordea.com/Our+services/Cash+Management/Products+and+services/IBAN+countries/908462.html">
 *   IBAN countries
 * </a>
 */
public enum IbanOfCountry {

    /**
     * Albania.
     */
    ALBANIA(Iso3166Country.ALBANIA, 28, "AL47212110090000000235698741"),

    /**
     * Algeria.
     */
    ALGERIA(Iso3166Country.ALGERIA, 24, "DZ4000400174401001050486"),

    /**
     * Andorra.
     */
    ANDORRA(Iso3166Country.ANDORRA, 24, "AD1200012030200359100100"),

    /**
     * Angola.
     */
    ANGOLA(Iso3166Country.ANGOLA, 25, "AO06000600000100037131174"),

    /**
     * Austria.
     */
    AUSTRIA(Iso3166Country.AUSTRIA, 20, "AT611904300235473201"),

    /**
     * Azerbaijan.
     */
    AZERBAIJAN(Iso3166Country.AZERBAIJAN, 28, "AZ21NABZ00000000137010001944"),

    /**
     * Bahrain.
     */
    BAHRAIN(Iso3166Country.BAHRAIN, 22, "BH29BMAG1299123456BH00"),

    /**
     * Belgium.
     */
    BELGIUM(Iso3166Country.BELGIUM, 16, "BE68539007547034"),

    /**
     * Benin.
     */
    BENIN(Iso3166Country.BENIN, 28, "BJ11B00610100400271101192591"),

    /**
     * Bosnia and Herzegovina.
     */
    BOSNIA_AND_HERZEGOVINA(Iso3166Country.BOSNIA_AND_HERZEGOVINA, 20, "BA391290079401028494"),

    /**
     * Brazil.
     */
    BRAZIL(Iso3166Country.BRAZIL, 29, "BR9700360305000010009795493P1"),

    /**
     * Bulgaria.
     */
    BULGARIA(Iso3166Country.BULGARIA, 22, "BG80BNBG96611020345678"),

    /**
     * Burkina Faso.
     */
    BURKINA_FASO(Iso3166Country.BURKINA_FASO, 27, "BF1030134020015400945000643"),

    /**
     * Burundi.
     */
    BURUNDI(Iso3166Country.BURUNDI, 16, "BI43201011067444"),

    /**
     * Cameroon.
     */
    CAMEROON(Iso3166Country.CAMEROON, 27, "CM2110003001000500000605306"),

    /**
     * Cabo Verde.
     */
    CABO_VERDE(Iso3166Country.CABO_VERDE, 25, "CV64000300004547069110176"),

    /**
     * Central African Republic.
     */
    CENTRAL_AFRICAN_REPUBLIC(Iso3166Country.CENTRAL_AFRICAN_REPUBLIC, 27, "FR7630007000110009970004942"),

    /**
     * Congo.
     */
    CONGO(Iso3166Country.CONGO, 27, "CG5230011000202151234567890"),

    /**
     * Costa Rica.
     */
    COSTA_RICA(Iso3166Country.COSTA_RICA, 21, "CR0515202001026284066"),

    /**
     * Ivory Coast.
     */
    COTE_DIVOIRE(Iso3166Country.COTE_DIVOIRE, 28, "CI05A00060174100178530011852"),

    /**
     * Croatia.
     */
    CROATIA(Iso3166Country.CROATIA, 21, "HR1210010051863000160"),

    /**
     * Cyprus.
     */
    CYPRUS(Iso3166Country.CYPRUS, 28, "CY17002001280000001200527600"),

    /**
     * Czechia.
     */
    CZECHIA(Iso3166Country.CZECHIA, 24, "CZ6508000000192000145399"),

    /**
     * Denmark.
     */
    DENMARK(Iso3166Country.DENMARK, 18, "DK5000400440116243"),

    /**
     * Dominican Republic.
     */
    DOMINICAN_REPUBLIC(Iso3166Country.DOMINICAN_REPUBLIC, 28, "DO28BAGR00000001212453611324"),

    /**
     * Egypt.
     */
    EGYPT(Iso3166Country.EGYPT, 27, "EG1100006001880800100014553"),

    /**
     * Estonia.
     */
    ESTONIA(Iso3166Country.ESTONIA, 20, "EE382200221020145685"),

    /**
     * Faroe Islands.
     */
    FAROE_ISLANDS(Iso3166Country.FAROE_ISLANDS, 18, "FO1464600009692713"),

    /**
     * Finland.
     */
    FINLAND(Iso3166Country.FINLAND, 18, "FI2112345600000785"),

    /**
     * France.
     */
    FRANCE(Iso3166Country.FRANCE, 27, "FR1420041010050500013M02606"),

    /**
     * French Guiana.
     */
    FRENCH_GUIANA(Iso3166Country.FRENCH_GUIANA, 27, FRANCE.getExample()),

    /**
     * French Polynesia.
     */
    FRENCH_POLYNESIA(Iso3166Country.FRENCH_POLYNESIA, 27, FRANCE.getExample()),

    /**
     * Gabon.
     */
    GABON(Iso3166Country.GABON, 27, "GA2140002000055602673300064"),

    /**
     * Georgia.
     */
    GEORGIA(Iso3166Country.GEORGIA, 22, "GE29NB0000000101904917"),

    /**
     * Germany.
     */
    GERMANY(Iso3166Country.GERMANY, 22, "DE89370400440532013000"),

    /**
     * Gibraltar.
     */
    GIBRALTAR(Iso3166Country.GIBRALTAR, 23, "GI75NWBK000000007099453"),

    /**
     * Greece.
     */
    GREECE(Iso3166Country.GREECE, 27, "GR1601101250000000012300695"),

    /**
     * Greenland.
     */
    GREENLAND(Iso3166Country.GREENLAND, 18, "GL8964710001000206"),

    /**
     * Guadeloupe.
     */
    GUADELOUPE(Iso3166Country.GUADELOUPE, 27, FRANCE.getExample()),

    /**
     * Guatemala.
     */
    GUATEMALA(Iso3166Country.GUATEMALA, 28, "GT82TRAJ01020000001210029690"),

    /**
     * Guernsey.
     */
    GUERNSEY(Iso3166Country.GUERNSEY, 22, "GB29NWBK60161331926819"),

    /**
     * Hungary.
     */
    HUNGARY(Iso3166Country.HUNGARY, 28, "HU42117730161111101800000000"),

    /**
     * Iceland.
     */
    ICELAND(Iso3166Country.ICELAND, 26, "IS140159260076545510730339"),

    /**
     * Iran.
     */
    IRAN_ISLAMIC_REPUBLIC_OF(Iso3166Country.IRAN_ISLAMIC_REPUBLIC_OF, 26, "IR580540105180021273113007"),

    /**
     * Ireland.
     */
    IRELAND(Iso3166Country.IRELAND, 22, "IE29AIBK93115212345678"),

    /**
     * Isle of Man.
     */
    ISLE_OF_MAN(Iso3166Country.ISLE_OF_MAN, 22, GUERNSEY.getExample()),

    /**
     * Israel.
     */
    ISRAEL(Iso3166Country.ISRAEL, 23, "IL620108000000099999999"),

    /**
     * Italy.
     */
    ITALY(Iso3166Country.ITALY, 27, "IT60X0542811101000000123456"),

    /**
     * Jersey.
     */
    JERSEY(Iso3166Country.JERSEY, 22, GUERNSEY.getExample()),

    /**
     * Jordan.
     */
    JORDAN(Iso3166Country.JORDAN, 30, "JO94CBJO0010000000000131000302"),

    /**
     * Kazakhstan.
     */
    KAZAKHSTAN(Iso3166Country.KAZAKHSTAN, 20, "KZ176010251000042993"),

    /**
     * Kuwait.
     */
    KUWAIT(Iso3166Country.KUWAIT, 30, "KW74NBOK0000000000001000372151"),

    /**
     * Latvia.
     */
    LATVIA(Iso3166Country.LATVIA, 21, "LV80BANK0000435195001"),

    /**
     * Lebanon.
     */
    LEBANON(Iso3166Country.LEBANON, 28, "LB30099900000001001925579115"),

    /**
     * Liechtenstein.
     */
    LIECHTENSTEIN(Iso3166Country.LIECHTENSTEIN, 21, "LI21088100002324013AA"),

    /**
     * Lithuania.
     */
    LITHUANIA(Iso3166Country.LITHUANIA, 20, "LT121000011101001000"),

    /**
     * Luxembourg.
     */
    LUXEMBOURG(Iso3166Country.LUXEMBOURG, 20, "LU280019400644750000"),

    /**
     * Macedonia.
     */
    MACEDONIA_THE_FORMER_YUGOSLAV_REPUBLIC_OF(
            Iso3166Country.MACEDONIA_THE_FORMER_YUGOSLAV_REPUBLIC_OF, 19, "MK07300000000042425"),

    /**
     * Madagascar.
     */
    MADAGASCAR(Iso3166Country.MADAGASCAR, 27, "MG4600005030010101914016056"),

    /**
     * Mali..
     */
    MALI(Iso3166Country.MALI, 28, "ML03D00890170001002120000447"),

    /**
     * Malta.
     */
    MALTA(Iso3166Country.MALTA, 31, "MT84MALT011000012345MTLCAST001S"),

    /**
     * Martinique.
     */
    MARTINIQUE(Iso3166Country.MARTINIQUE, 27, FRANCE.getExample()),

    /**
     * Mauritania.
     */
    MAURITANIA(Iso3166Country.MAURITANIA, 27, "MR1300012000010000002037372"),

    /**
     * Mauritius.
     */
    MAURITIUS(Iso3166Country.MAURITIUS, 30, "MU17BOMM0101101030300200000MUR"),

    /**
     * Moldova.
     */
    MOLDOVA_REPUBLIC_OF(Iso3166Country.MOLDOVA_REPUBLIC_OF, 24, "MD24AG000225100013104168"),

    /**
     * Monaco.
     */
    MONACO(Iso3166Country.MONACO, 27, "MC5813488000010051108001292"),

    /**
     * Montenegro.
     */
    MONTENEGRO(Iso3166Country.MONTENEGRO, 22, "ME25505000012345678951"),

    /**
     * Mozambique.
     */
    MOZAMBIQUE(Iso3166Country.MOZAMBIQUE, 25, "MZ59000100000011834194157"),

    /**
     * Netherlands.
     */
    NETHERLANDS(Iso3166Country.NETHERLANDS, 18, "NL91ABNA0417164300"),

    /**
     * New Caledonia.
     */
    NEW_CALEDONIA(Iso3166Country.NEW_CALEDONIA, 27, FRANCE.getExample()),

    /**
     * Norway.
     */
    NORWAY(Iso3166Country.NORWAY, 15, "NO9386011117947"),

    /**
     * Pakistan.
     */
    PAKISTAN(Iso3166Country.PAKISTAN, 24, "PK24SCBL0000001171495101"),

    /**
     * Palestine.
     */
    PALESTINE(Iso3166Country.PALESTINE, 24, "PK24SCBL0000001171495101"),

    /**
     * Poland.
     */
    POLAND(Iso3166Country.POLAND, 28, "PL27114020040000300201355387"),

    /**
     * Qatar.
     */
    QATAR(Iso3166Country.QATAR, 29, "QA58DOHB00001234567890ABCDEFG"),

    /**
     * Portugal.
     */
    PORTUGAL(Iso3166Country.PORTUGAL, 25, "PT50000201231234567890154"),

    /**
     * Réunion.
     */
    REUNION(Iso3166Country.REUNION, 27, FRANCE.getExample()),

    /**
     * Romania.
     */
    ROMANIA(Iso3166Country.ROMANIA, 24, "RO49AAAA1B31007593840000"),

    /**
     * Saint-Pierre and Miquelon.
     */
    SAINT_PIERRE_AND_MIQUELON(Iso3166Country.SAINT_PIERRE_AND_MIQUELON, 27, FRANCE.getExample()),

    /**
     * San Marino.
     */
    SAN_MARINO(Iso3166Country.SAN_MARINO, 27, "SM86U0322509800000000270100"),

    /**
     * Sao Tome and Principe.
     */
    SAO_TOME_AND_PRINCIPE(Iso3166Country.SAO_TOME_AND_PRINCIPE, 25, "PT50000200000163099310355"),

    /**
     * Saudi Arabia.
     */
    SAUDI_ARABIA(Iso3166Country.SAUDI_ARABIA, 24, "SA0380000000608010167519"),

    /**
     * Senegal.
     */
    SENEGAL(Iso3166Country.SENEGAL, 28, "SN12K00100152000025690007542"),

    /**
     * Serbia.
     */
    SERBIA(Iso3166Country.SERBIA, 22, "RS35260005601001611379"),

    /**
     * Slovakia.
     */
    SLOVAKIA(Iso3166Country.SLOVAKIA, 24, "SK3112000000198742637541"),

    /**
     * Slovenia.
     */
    SLOVENIA(Iso3166Country.SLOVENIA, 19, "SI56191000000123438"),

    /**
     * Spain.
     */
    SPAIN(Iso3166Country.SPAIN, 24, "ES9121000418450200051332"),

    /**
     * Sweden.
     */
    SWEDEN(Iso3166Country.SWEDEN, 24, "SE3550000000054910000003"),

    /**
     * Switzerland.
     */
    SWITZERLAND(Iso3166Country.SWITZERLAND, 21, "CH9300762011623852957"),

    /**
     * Tunisia.
     */
    TUNISIA(Iso3166Country.TUNISIA, 24, "TN5914207207100707129648"),

    /**
     * Turkey.
     */
    TURKEY(Iso3166Country.TURKEY, 26, "TR330006100519786457841326"),

    /**
     * Ukraine.
     */
    UKRAINE(Iso3166Country.UKRAINE, 29, "UA573543470006762462054925026"),

    /**
     * United Arab Emirates.
     */
    UNITED_ARAB_EMIRATES(Iso3166Country.UNITED_ARAB_EMIRATES, 23, "AE260211000000230064016"),

    /**
     * United-Kingdom.
     */
    UNITED_KINGDOM(Iso3166Country.UNITED_KINGDOM, 22, GUERNSEY.getExample()),

    /**
     * British Virgin Islands.
     */
    VIRGIN_ISLANDS_BRITISH(Iso3166Country.VIRGIN_ISLANDS_BRITISH, 24, "VG96VPVG0000012345678901"),

    /**
     * Wallis and Futuna.
     */
    WALLIS_AND_FUTUNA(Iso3166Country.WALLIS_AND_FUTUNA, 27, FRANCE.getExample());

    /**
     * The country where the IBAN is used.
     */
    private final Iso3166Country country;

    /**
     * Length of the IBAN.
     */
    private final int length;

    /**
     * Example IBAN.
     */
    private final String example;

    /**
     * Constructs this ÍbanOfCountry from the supplied length and example IBAN.
     * @param country The country where the IBAN is used.
     * @param length The length of the IBAN.
     * @param example An example IBAN for the supplied country.
     */
    IbanOfCountry(final Iso3166Country country, final int length, final String example) {
        assert example.length() == length : "Illegal IBAN example: lengths differ.";

        this.country = country;
        this.length = length;
        this.example = example;
    }

    /**
     * Gets the country where this IBAN is used.
     * @return The country where this IBAN is used.
     */
    public Iso3166Country getCountry() {
        return this.country;
    }

    /**
     * Gets the length of the IBAN.
     * @return The length of the IBAN.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Gets the example IBAN.
     * @return IBAN example.
     */
    public String getExample() {
        return this.example;
    }

    /**
     * Gets the IbanOfCountry corresponding to the supplied country.
     * @param country The country.
     * @return The IbanOfCountry corresponding to the supplied country.
     * @throws NullPointerException When the supplied country is null.
     */
    public static IbanOfCountry valueOf(final Iso3166Country country)  {
        for (final IbanOfCountry ibanOfCountry : IbanOfCountry.values()) {
            if (ibanOfCountry.getCountry() == country) {
                return ibanOfCountry;
            }
        }

        return null;
    }

}
