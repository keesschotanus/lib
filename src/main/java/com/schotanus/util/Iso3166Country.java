/*
 * Copyright (C) 2009 Kees Schotanus
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

package com.schotanus.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Enumeration of ISO 3166 Countries.
 * <br>The list of countries is maintained
 * <a href="http://www.iso.org/iso/country_codes/iso_3166_code_lists/english_country_names_and_code_elements.htm">here</a>.<br>
 * The Dutch translations can be found <a href="http://nl.wikipedia.org/wiki/ISO_3166-1">here</a>.<br>
 * The French translations can be found <a href="http://www.iso.org/iso/french_country_names_and_code_elements">here</a>.<br>
 * The German translations can be found <a href="http://de.wikipedia.org/wiki/ISO-3166-1-Kodierliste">here</a>.<br>
 * To subscribe to changes to the ISO 3166 standard surf to
 * <a href="http://www.iso.org/iso/home/standards/country_codes/updates_on_iso_3166.htm">here</a>.<br>
 * Note: Except for the Netherlands Antilles, this Enumeration does not contain any countries yet, that are not active.<br>
 * Note: The {@link ZipCodeOfCountry} class is tightly linked to this class.
 * When changing this class you probably want to change the ZipCodeOfCountry class as well.<br>
 * Even though not tightly linked, you probably want to change the {@link Nationality} class as well.<br>
 * Note: The name of the enumerated constant is created from the short name where spaces are replaced with underscores.
 * For example: "UNITED STATES OF AMERICA", becomes "UNITED_STATES_OF_AMERICA".
 * When the name contains parenthesis, or other non-alphabetic characters, they are stripped.
 * For example: "KOREA (DEMOCRATIC PEOPLE'S REPUBLIC OF)", becomes "KOREA_DEMOCRATIC_PEOPLES_REPUBLIC_OF".
 * The description in the properties file is the "short name, lower case".
 * Sometimes the name is adjusted slightly for better readability.
 * For example: "United States of America (the)", is translated to: "United States of America".
 * @see Nationality
 * @see ZipCodeOfCountry
 */
public enum Iso3166Country {

    /**
     * AFGHANISTAN.
     */
    AFGHANISTAN("AF"),
    /**
     * ALAND ISLANDS.
     */
    ALAND_ISLANDS("AX"),
    /**
     * ALBANIA.
     */
    ALBANIA("AL"),
    /**
     * ALGERIA.
     */
    ALGERIA("DZ"),
    /**
     * AMERICAN SAMOA.
     */
    AMERICAN_SAMOA("AS"),
    /**
     * ANDORRA.
     */
    ANDORRA("AD"),
    /**
     * ANGOLA.
     */
    ANGOLA("AO"),
    /**
     * ANGUILLA.
     */
    ANGUILLA("AI"),
    /**
     * ANTARCTICA.
     */
    ANTARCTICA("AQ"),
    /**
     * ANTIGUA AND BARBUDA.
     */
    ANTIGUA_AND_BARBUDA("AG"),
    /**
     * ARGENTINA.
     */
    ARGENTINA("AR"),
    /**
     * ARMENIA.
     */
    ARMENIA("AM"),
    /**
     * ARUBA.
     */
    ARUBA("AW"),
    /**
     * AUSTRALIA.
     */
    AUSTRALIA("AU"),
    /**
     * AUSTRIA.
     */
    AUSTRIA("AT"),
    /**
     * AZERBAIJAN.
     */
    AZERBAIJAN("AZ"),
    /**
     * BAHAMAS.
     */
    BAHAMAS("BS"),
    /**
     * BAHRAIN.
     */
    BAHRAIN("BH"),
    /**
     * BANGLADESH.
     */
    BANGLADESH("BD"),
    /**
     * BARBADOS.
     */
    BARBADOS("BB"),
    /**
     * BELARUS.
     */
    BELARUS("BY"),
    /**
     * BELGIUM.
     */
    BELGIUM("BE"),
    /**
     * BELIZE.
     */
    BELIZE("BZ"),
    /**
     * BENIN.
     */
    BENIN("BJ"),
    /**
     * BERMUDA.
     */
    BERMUDA("BM"),
    /**
     * BHUTAN.
     */
    BHUTAN("BT"),
    /**
     * BOLIVIA, PLURINATIONAL STATE OF.
     */
    BOLIVIA_PLURINATIONAL_STATE_OF("BO"),
    /**
     * BONAIRE, SAINT EUSTATIUS AND SABA.
     */
    BONAIRE_SAINT_EUSTATIUS_AND_SABA("BQ"),
    /**
     * BOSNIA AND HERZEGOVINA.
     */
    BOSNIA_AND_HERZEGOVINA("BA"),
    /**
     * BOTSWANA.
     */
    BOTSWANA("BW"),
    /**
     * BOUVET ISLAND.
     */
    BOUVET_ISLAND("BV"),
    /**
     * BRAZIL.
     */
    BRAZIL("BR"),
    /**
     * BRITISH INDIAN OCEAN TERRITORY.
     */
    BRITISH_INDIAN_OCEAN_TERRITORY("IO"),
    /**
     * BRUNEI DARUSSALAM.
     */
    BRUNEI_DARUSSALAM("BN"),
    /**
     * BULGARIA.
     */
    BULGARIA("BG"),
    /**
     * BURKINA FASO.
     */
    BURKINA_FASO("BF"),
    /**
     * BURUNDI.
     */
    BURUNDI("BI"),
    /**
     * CAMBODIA.
     */
    CAMBODIA("KH"),
    /**
     * CAMEROON.
     */
    CAMEROON("CM"),
    /**
     * CANADA.
     */
    CANADA("CA"),
    /**
     * CABO VERDE.
     */
    CABO_VERDE("CV"),
    /**
     * CAYMAN ISLANDS.
     */
    CAYMAN_ISLANDS("KY"),
    /**
     * CENTRAL AFRICAN REPUBLIC.
     */
    CENTRAL_AFRICAN_REPUBLIC("CF"),
    /**
     * CHAD.
     */
    CHAD("TD"),
    /**
     * CHILE.
     */
    CHILE("CL"),
    /**
     * CHINA.
     */
    CHINA("CN"),
    /**
     * CHRISTMAS ISLAND.
     */
    CHRISTMAS_ISLAND("CX"),
    /**
     * COCOS (KEELING) ISLANDS.
     */
    COCOS_KEELING_ISLANDS("CC"),
    /**
     * COLOMBIA.
     */
    COLOMBIA("CO"),
    /**
     * COMOROS.
     */
    COMOROS("KM"),
    /**
     * CONGO.
     */
    CONGO("CG"),
    /**
     * CONGO THE DEMOCRATIC REPUBLIC OF THE.
     */
    CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE("CD"),
    /**
     * COOK ISLANDS.
     */
    COOK_ISLANDS("CK"),
    /**
     * COSTA RICA.
     */
    COSTA_RICA("CR"),
    /**
     * CoTE D'IVOIRE.
     */
    COTE_DIVOIRE("CI"),
    /**
     * CROATIA.
     */
    CROATIA("HR"),
    /**
     * CUBA.
     */
    CUBA("CU"),
    /**
     * CURACAO.
     */
    CURACAO("CW"),
    /**
     * CYPRUS.
     */
    CYPRUS("CY"),
    /**
     * CZECHIA.
     */
    CZECHIA("CZ"),
    /**
     * DENMARK.
     */
    DENMARK("DK"),
    /**
     * DJIBOUTI.
     */
    DJIBOUTI("DJ"),
    /**
     * DOMINICA.
     */
    DOMINICA("DM"),
    /**
     * DOMINICAN REPUBLIC.
     */
    DOMINICAN_REPUBLIC("DO"),
    /**
     * ECUADOR.
     */
    ECUADOR("EC"),
    /**
     * EGYPT.
     */
    EGYPT("EG"),
    /**
     * EL SALVADOR.
     */
    EL_SALVADOR("SV"),
    /**
     * EQUATORIAL GUINEA.
     */
    EQUATORIAL_GUINEA("GQ"),
    /**
     * ERITREA.
     */
    ERITREA("ER"),
    /**
     * ESTONIA.
     */
    ESTONIA("EE"),
    /**
     * ETHIOPIA.
     */
    ETHIOPIA("ET"),
    /**
     * FALKLAND ISLANDS (MALVINAS).
     */
    FALKLAND_ISLANDS_MALVINAS("FK"),
    /**
     * FAROE ISLANDS.
     */
    FAROE_ISLANDS("FO"),
    /**
     * FIJI.
     */
    FIJI("FJ"),
    /**
     * FINLAND.
     */
    FINLAND("FI"),
    /**
     * FRANCE.
     */
    FRANCE("FR"),
    /**
     * FRENCH GUIANA.
     */
    FRENCH_GUIANA("GF"),
    /**
     * FRENCH POLYNESIA.
     */
    FRENCH_POLYNESIA("PF"),
    /**
     * FRENCH SOUTHERN TERRITORIES.
     */
    FRENCH_SOUTHERN_TERRITORIES("TF"),
    /**
     * GABON.
     */
    GABON("GA"),
    /**
     * GAMBIA.
     */
    GAMBIA("GM"),
    /**
     * GEORGIA.
     */
    GEORGIA("GE"),
    /**
     * GERMANY DEMOCRATIC REPUBLIC.
     */
    GERMANY_DEMOCRATIC_REPUBLIC("DD", LocalDate.of(DateConstants.YEAR_1974, Month.DECEMBER, DateConstants.DAY_15),
            LocalDate.of(DateConstants.YEAR_1990, Month.DECEMBER, DateConstants.DAY_4)),
    /**
     * GERMANY.
     */
    GERMANY("DE"),
    /**
     * GHANA.
     */
    GHANA("GH"),
    /**
     * GIBRALTAR.
     */
    GIBRALTAR("GI"),
    /**
     * GREECE.
     */
    GREECE("GR"),
    /**
     * GREENLAND.
     */
    GREENLAND("GL"),
    /**
     * GRENADA.
     */
    GRENADA("GD"),
    /**
     * GUADELOUPE.
     */
    GUADELOUPE("GP"),
    /**
     * GUAM.
     */
    GUAM("GU"),
    /**
     * GUATEMALA.
     */
    GUATEMALA("GT"),
    /**
     * GUERNSEY.
     */
    GUERNSEY("GG"),
    /**
     * GUINEA.
     */
    GUINEA("GN"),
    /**
     * GUINEA-BISSAU.
     */
    GUINEA_BISSAU("GW"),
    /**
     * GUYANA.
     */
    GUYANA("GY"),
    /**
     * HAITI.
     */
    HAITI("HT"),
    /**
     * HEARD ISLAND AND MCDONALD ISLANDS.
     */
    HEARD_ISLAND_AND_MCDONALD_ISLANDS("HM"),
    /**
     * HONDURAS.
     */
    HONDURAS("HN"),
    /**
     * HONG_KONG.
     */
    HONG_KONG("HK"),
    /**
     * HUNGARY.
     */
    HUNGARY("HU"),
    /**
     * ICELAND.
     */
    ICELAND("IS"),
    /**
     * INDIA.
     */
    INDIA("IN"),
    /**
     * INDONESIA.
     */
    INDONESIA("ID"),
    /**
     * IRAN ISLAMIC REPUBLIC OF.
     */
    IRAN_ISLAMIC_REPUBLIC_OF("IR"),
    /**
     * IRAQ.
     */
    IRAQ("IQ"),
    /**
     * IRELAND.
     */
    IRELAND("IE"),
    /**
     * ISLE OF MAN.
     */
    ISLE_OF_MAN("IM"),
    /**
     * ISRAEL.
     */
    ISRAEL("IL"),
    /**
     * ITALY.
     */
    ITALY("IT"),
    /**
     * JAMAICA.
     */
    JAMAICA("JM"),
    /**
     * JAPAN.
     */
    JAPAN("JP"),
    /**
     * JERSEY.
     */
    JERSEY("JE"),
    /**
     * JORDAN.
     */
    JORDAN("JO"),
    /**
     * KAZAKHSTAN.
     */
    KAZAKHSTAN("KZ"),
    /**
     * KENYA.
     */
    KENYA("KE"),
    /**
     * KIRIBATI.
     */
    KIRIBATI("KI"),
    /**
     * KOREA DEMOCRATIC PEOPLE'S REPUBLIC OF.
     */
    KOREA_DEMOCRATIC_PEOPLES_REPUBLIC_OF("KP"),
    /**
     * KOREA REPUBLIC OF.
     */
    KOREA_REPUBLIC_OF("KR"),
    /**
     * KUWAIT.
     */
    KUWAIT("KW"),
    /**
     * KYRGYZSTAN.
     */
    KYRGYZSTAN("KG"),
    /**
     * LAO PEOPLE'S DEMOCRATIC REPUBLIC.
     */
    LAO_PEOPLES_DEMOCRATIC_REPUBLIC("LA"),
    /**
     * LATVIA.
     */
    LATVIA("LV"),
    /**
     * LEBANON.
     */
    LEBANON("LB"),
    /**
     * LESOTHO.
     */
    LESOTHO("LS"),
    /**
     * LIBERIA.
     */
    LIBERIA("LR"),
    /**
     * LIBYA, THE STATE OF.
     */
    LIBYA("LY"),
    /**
     * LIECHTENSTEIN.
     */
    LIECHTENSTEIN("LI"),
    /**
     * LITHUANIA.
     */
    LITHUANIA("LT"),
    /**
     * LUXEMBOURG.
     */
    LUXEMBOURG("LU"),
    /**
     * MACAO.
     */
    MACAO("MO"),
    /**
     * MACEDONIA, THE_FORMER YUGOSLAV REPUBLIC OF.
     */
    MACEDONIA_THE_FORMER_YUGOSLAV_REPUBLIC_OF("MK"),
    /**
     * MADAGASCAR.
     */
    MADAGASCAR("MG"),
    /**
     * MALAWI.
     */
    MALAWI("MW"),
    /**
     * MALAYSIA.
     */
    MALAYSIA("MY"),
    /**
     * MALDIVES.
     */
    MALDIVES("MV"),
    /**
     * MALI.
     */
    MALI("ML"),
    /**
     * MALTA.
     */
    MALTA("MT"),
    /**
     * MARSHALL ISLANDS.
     */
    MARSHALL_ISLANDS("MH"),
    /**
     * MARTINIQUE.
     */
    MARTINIQUE("MQ"),
    /**
     * MAURITANIA.
     */
    MAURITANIA("MR"),
    /**
     * MAURITIUS.
     */
    MAURITIUS("MU"),
    /**
     * MAYOTTE.
     */
    MAYOTTE("YT"),
    /**
     * MEXICO.
     */
    MEXICO("MX"),
    /**
     * MICRONESIA, FEDERATED STATES OF.
     */
    MICRONESIA_FEDERATED_STATES_OF("FM"),
    /**
     * MOLDOVA, REPUBLIC OF.
     */
    MOLDOVA_REPUBLIC_OF("MD"),
    /**
     * MONACO.
     */
    MONACO("MC"),
    /**
     * MONGOLIA.
     */
    MONGOLIA("MN"),
    /**
     * MONTENEGRO.
     */
    MONTENEGRO("ME"),
    /**
     * MONTSERRAT.
     */
    MONTSERRAT("MS"),
    /**
     * MOROCCO.
     */
    MOROCCO("MA"),
    /**
     * MOZAMBIQUE.
     */
    MOZAMBIQUE("MZ"),
    /**
     * MYANMAR.
     */
    MYANMAR("MM"),
    /**
     * NAMIBIA.
     */
    NAMIBIA("NA"),
    /**
     * NAURU.
     */
    NAURU("NR"),
    /**
     * NEPAL.
     */
    NEPAL("NP"),
    /**
     * NETHERLANDS.
     */
    NETHERLANDS("NL"),
    /**
     * NETHERLANDS ANTILLES.
     */
    NETHERLANDS_ANTILLES("AN",
            LocalDate.of(DateConstants.YEAR_2010, Month.DECEMBER, DateConstants.DAY_15)),
    /**
     * NEW CALEDONIA.
     */
    NEW_CALEDONIA("NC"),
    /**
     * NEW ZEALAND.
     */
    NEW_ZEALAND("NZ"),
    /**
     * NICARAGUA.
     */
    NICARAGUA("NI"),
    /**
     * NIGER.
     */
    NIGER("NE"),
    /**
     * NIGERIA.
     */
    NIGERIA("NG"),
    /**
     * NIUE.
     */
    NIUE("NU"),
    /**
     * NORFOLK ISLAND.
     */
    NORFOLK_ISLAND("NF"),
    /**
     * NORTHERN MARIANA ISLANDS.
     */
    NORTHERN_MARIANA_ISLANDS("MP"),
    /**
     * NORWAY.
     */
    NORWAY("NO"),
    /**
     * OMAN.
     */
    OMAN("OM"),
    /**
     * PAKISTAN.
     */
    PAKISTAN("PK"),
    /**
     * PALAU.
     */
    PALAU("PW"),
    /**
     * PALESTINIAN TERRITORY, OCCUPIED.
     */
    PALESTINE("PS"),
    /**
     * PANAMA.
     */
    PANAMA("PA"),
    /**
     * PAPUA NEW GUINEA.
     */
    PAPUA_NEW_GUINEA("PG"),
    /**
     * PARAGUAY.
     */
    PARAGUAY("PY"),
    /**
     * PERU.
     */
    PERU("PE"),
    /**
     * PHILIPPINES.
     */
    PHILIPPINES("PH"),
    /**
     * PITCAIRN.
     */
    PITCAIRN("PN"),
    /**
     * POLAND.
     */
    POLAND("PL"),
    /**
     * PORTUGAL.
     */
    PORTUGAL("PT"),
    /**
     * PUERTO RICO.
     */
    PUERTO_RICO("PR"),
    /**
     * QATAR.
     */
    QATAR("QA"),
    /**
     * REUNION.
     */
    REUNION("RE"),
    /**
     * ROMANIA.
     */
    ROMANIA("RO"),
    /**
     * RUSSIAN FEDERATION.
     */
    RUSSIAN_FEDERATION("RU"),
    /**
     * RWANDA.
     */
    RWANDA("RW"),
    /**
     * SAINT_BARTHELEMY.
     */
    SAINT_BARTHELEMY("BL"),
    /**
     * SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA.
     */
    SAINT_HELENA_ASCENSION_AND_TRISTAN_DA_CUNHA("SH"),
    /**
     * SAINT KITTS AND NEVIS.
     */
    SAINT_KITTS_AND_NEVIS("KN"),
    /**
     * SAINT LUCIA.
     */
    SAINT_LUCIA("LC"),
    /**
     * SAINT MARTIN (FRENCH PART).
     */
    SAINT_MARTIN_FRENCH_PART("MF"),
    /**
     * SAINT PIERRE AND MIQUELON.
     */
    SAINT_PIERRE_AND_MIQUELON("PM"),
    /**
     * SAINT VINCENT AND THE GRENADINES.
     */
    SAINT_VINCENT_AND_THE_GRENADINES("VC"),
    /**
     * SAMOA.
     */
    SAMOA("WS"),
    /**
     * SAN MARINO.
     */
    SAN_MARINO("SM"),
    /**
     * SAO TOME AND PRINCIPE.
     */
    SAO_TOME_AND_PRINCIPE("ST"),
    /**
     * SAUDI ARABIA.
     */
    SAUDI_ARABIA("SA"),
    /**
     * SENEGAL.
     */
    SENEGAL("SN"),
    /**
     * SERBIA.
     */
    SERBIA("RS"),
    /**
     * SEYCHELLES.
     */
    SEYCHELLES("SC"),
    /**
     * SIERRA LEONE.
     */
    SIERRA_LEONE("SL"),
    /**
     * SINGAPORE.
     */
    SINGAPORE("SG"),
    /**
     * SINT MAARTEN (DUTCH PART).
     */
    SINT_MAARTEN_DUTCH_PART("SX"),
    /**
     * SLOVAKIA.
     */
    SLOVAKIA("SK"),
    /**
     * SLOVENIA.
     */
    SLOVENIA("SI"),
    /**
     * SOLOMON ISLANDS.
     */
    SOLOMON_ISLANDS("SB"),
    /**
     * SOMALIA.
     */
    SOMALIA("SO"),
    /**
     * SOUTH AFRICA.
     */
    SOUTH_AFRICA("ZA"),
    /**
     * SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS.
     */
    SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS("GS"),
    /**
     * SOUTH SUDAN.
     */
    SOUTH_SUDAN("SS"),
    /**
     * SPAIN.
     */
    SPAIN("ES"),
    /**
     * SRI LANKA.
     */
    SRI_LANKA("LK"),
    /**
     * SUDAN.
     */
    SUDAN("SD"),
    /**
     * SURINAME.
     */
    SURINAME("SR"),
    /**
     * SVALBARD AND JAN MAYEN.
     */
    SVALBARD_AND_JAN_MAYEN("SJ"),
    /**
     * ESWATINI.
     */
    ESWATINI("SZ"),
    /**
     * SWEDEN.
     */
    SWEDEN("SE"),
    /**
     * SWITZERLAND.
     */
    SWITZERLAND("CH"),
    /**
     * SYRIAN ARAB REPUBLIC.
     */
    SYRIAN_ARAB_REPUBLIC("SY"),
    /**
     * TAIWAN, PROVINCE OF CHINA.
     */
    TAIWAN_PROVINCE_OF_CHINA("TW"),
    /**
     * TAJIKISTAN.
     */
    TAJIKISTAN("TJ"),
    /**
     * TANZANIA, UNITED REPUBLIC OF.
     */
    TANZANIA_UNITED_REPUBLIC_OF("TZ"),
    /**
     * THAILAND.
     */
    THAILAND("TH"),
    /**
     * TIMOR LESTE.
     */
    TIMOR_LESTE("TL"),
    /**
     * TOGO.
     */
    TOGO("TG"),
    /**
     * TOKELAU.
     */
    TOKELAU("TK"),
    /**
     * TONGA.
     */
    TONGA("TO"),
    /**
     * TRINIDAD AND TOBAGO.
     */
    TRINIDAD_AND_TOBAGO("TT"),
    /**
     * TUNISIA.
     */
    TUNISIA("TN"),
    /**
     * TURKEY.
     */
    TURKEY("TR"),
    /**
     * TURKMENISTAN.
     */
    TURKMENISTAN("TM"),
    /**
     * TURKS AND CAICOS ISLANDS.
     */
    TURKS_AND_CAICOS_ISLANDS("TC"),
    /**
     * TUVALU.
     */
    TUVALU("TV"),
    /**
     * UGANDA.
     */
    UGANDA("UG"),
    /**
     * UKRAINE.
     */
    UKRAINE("UA"),
    /**
     * UNITED ARAB EMIRATES.
     */
    UNITED_ARAB_EMIRATES("AE"),
    /**
     * UNITED KINGDOM.
     */
    UNITED_KINGDOM("GB"),
    /**
     * UNITED STATES.
     */
    UNITED_STATES_OF_AMERICA("US"),
    /**
     * UNITED STATES MINOR OUTLYING ISLANDS.
     */
    UNITED_STATES_MINOR_OUTLYING_ISLANDS("UM"),
    /**
     * URUGUAY.
     */
    URUGUAY("UY"),
    /**
     * UZBEKISTAN.
     */
    UZBEKISTAN("UZ"),
    /**
     * VANUATU.
     */
    VANUATU("VU"),
    /**
     * VATICAN CITY STATE.
     */
    VATICAN_CITY_STATE("VA"),

    /**
     * VENEZUELA, BOLIVARIAN REPUBLIC OF.
     */
    VENEZUELA_BOLIVARIAN_REPUBLIC_OF("VE"),
    /**
     * VIET_NAM.
     */
    VIET_NAM("VN"),
    /**
     * VIRGIN ISLANDS, BRITISH.
     */
    VIRGIN_ISLANDS_BRITISH("VG"),
    /**
     * VIRGIN ISLANDS, U.S..
     */
    VIRGIN_ISLANDS_US("VI"),
    /**
     * WALLIS AND FUTUNA.
     */
    WALLIS_AND_FUTUNA("WF"),
    /**
     * WESTERN SAHARA.
     */
    WESTERN_SAHARA("EH"),
    /**
     * YEMEN.
     */
    YEMEN("YE"),
    /**
     * ZAMBIA.
     */
    ZAMBIA("ZM"),
    /**
     * ZIMBABWE.
     */
    ZIMBABWE("ZW");

    /**
     * Key to lookup: Illegal country code.<br>
     * Arguments: <ul><li>country code</li></ul>.
     */
    public static final String MSG_ILLEGAL_COUNTRY_CODE = "illegalCountryCode";

    /**
     * A map of alpha-2code to the corresponding country.
     */
    private static final Map<String, Iso3166Country> codeToCountry = new HashMap<>(Iso3166Country.values().length);

    /*
     * Put all countries in a map where the key is the 2-letter alpha code and the value is the corresponding country.
     */
    static {
        for (final Iso3166Country country : Iso3166Country.values()) {
            codeToCountry.put(country.alpha2Code, country);
        }
    }

    /**
     * Two-letter code for this Iso3166Country.
     */
    private final String alpha2Code;

    /**
     * Date from which this country is active or null when this country is active from the beginning of publication.
     */
    private LocalDate activeFrom;

    /**
     * Date until this country is active or null when this country is currently active.
     */
    private LocalDate activeUntil;

    /**
     * Resource bundle for this class.
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(Iso3166Country.class.getName());

    /**
     * Constructs this Iso3166Country and stores the supplied alpha2Code.
     * @param alpha2Code The 2-letter country code.
     */
    Iso3166Country(final String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    /**
     * Constructs this Iso3166Country that is active from the beginning, until the supplied activeUntil.
     * @param alpha2Code The 2-letter country code.
     * @param activeUntil Date until this country is active.
     *  <br>Null may be supplied for a country that is currently active.
     */
    Iso3166Country(final String alpha2Code, final LocalDate activeUntil) {
        this.alpha2Code = alpha2Code;
        this.activeUntil = activeUntil;
    }

    /**
     * Constructs this Iso3166Country that is valid from the supplied activeFrom, until the supplied activeUntil.
     * @param alpha2Code The 2-letter country code.
     * @param activeFrom Date from which this country is active.
     *  <br>Null may be supplied for a country that is active from the beginning.
     * @param activeUntil Date until this country is active.
     *  <br>Null may be supplied for a country that is still active.
     */
    Iso3166Country(final String alpha2Code, final LocalDate activeFrom, final LocalDate activeUntil) {
        this.alpha2Code = alpha2Code;
        this.activeFrom = activeFrom;
        this.activeUntil = activeUntil;
    }

    /**
     * Gets the 2-letter country code.
     * @return The 2-letter country code.
     */
    public String getAlpha2Code() {
        return this.alpha2Code;
    }

    /**
     * Determines whether this country is currently active or not.
     * @return True when this country is active, false when inactive.
     */
    public boolean isActive() {
        return isActive(LocalDate.now());
    }

    /**
     * Determines whether this country is active or not, at the specified date.
     * @param date Date used to check whether this country was active at that date.
     * @return True when this country is active, false when inactive.
     * @throws NullPointerException When the supplied date is null.
     */
    public boolean isActive(final LocalDate date) {
        Objects.requireNonNull(date);

        boolean result;
        if (this.activeFrom == null && this.activeUntil == null) {
            result = true;
        } else if (this.activeFrom == null) {
            result = date.isBefore(this.activeUntil);
        } else if (this.activeUntil == null) {
            result = date.isAfter(this.activeFrom);
        } else {
            // Both activeFrom and activeUntil are non-null
            result = date.isAfter(this.activeFrom) && date.isBefore(this.activeUntil);
        }

        return result;
    }

    /**
     * Gets the date from which this country is active or null when this country was active from the beginning.
     * @return The date from which this country is active.
     *  <br>Null is returned when this country is active from the beginning.
     */
    public LocalDate getActiveFrom() {
        return this.activeFrom;
    }

    /**
     * Gets the date until this country is active or null when this country is currently active.
     * @return The date until this country is active.
     *  <br>Null is returned when this country is still active.
     */
    public LocalDate getActiveUntil() {
        return this.activeUntil;
    }

    /**
     * Gets a List of {@link CodeDescription} objects for all the active ISO-3166 countries, ordered by name of the country.
     * @param locale Locale used to localize the descriptions.
     *  <br>When null is supplied the default locale is used.
     * @param activeAt Determines at what date the countries must be active.
     *  <br>When null is supplied the current date is used.
     * @return A List of {@link CodeDescription} objects for all the active ISO-3166 countries ordered by name of the country.
     */
    public static List<CodeDescription<String>> getCodeDescriptions(final Locale locale, final LocalDate activeAt) {
        final LocalDate toDay = Objects.requireNonNullElse(activeAt, LocalDate.now());

        return Arrays.stream(Iso3166Country.values())
                .filter(country -> country.isActive(toDay))
                .map(country -> CEnumeration.getCodeDescription(country, country.getAlpha2Code(), locale))
                .sorted(new CodeDescriptionComparator<>())
                .toList();
    }

    /**
     * Determines whether the supplied country code is a valid country code.
     * <br>When the supplied alpha2Code is valid, it does not imply that the corresponding country is active.
     * See: {@link #isActive()} or {@link #isActive(LocalDate)}.
     * @param alpha2Code The 2 letter alpha code to check.
     *  <br>This parameter is converted to upper-case before being checked.
     * @return True when the supplied alpha2Code corresponds to a valid country, otherwise false is returned.
     */
    public static boolean isValidAlpha2Code(final String alpha2Code) {
        return codeToCountry.containsKey(alpha2Code.toUpperCase(java.util.Locale.getDefault()));
    }

    /**
     * Gets the Iso31166Country corresponding to the supplied alpha2Code.
     * <br>The returned country does not have to be active.
     * See: {@link #isActive()} or {@link #isActive(LocalDate)}.
     * @param alpha2Code The alpha2Code of the country to get.
     * @return The Iso31166Country corresponding to the supplied alpha2Code.
     * @throws IllegalArgumentException When no country with the supplied alpha2Code exists.
     */
    public static Iso3166Country getIso3166Country(final String alpha2Code) {
        final Iso3166Country country = codeToCountry.get(alpha2Code);
        if (country == null) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_ILLEGAL_COUNTRY_CODE, alpha2Code));
        }

        return country;
    }

}
