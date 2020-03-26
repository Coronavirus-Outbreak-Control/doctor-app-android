package com.example.coronavirusherdimmunitydoctor.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.coronavirusherdimmunitydoctor.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created a Spinner extension "CountryCodeSpinner"
 */

public class CountryCodeSpinner extends AppCompatSpinner {

    private ArrayList<CountryModel> arrayList;
    private CountryCodeAdapter countryCodeAdapter;

    public CountryCodeSpinner(Context context) {
        super(context);
    }

    public CountryCodeSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        fillCountryList();
        countryCodeAdapter = new CountryCodeAdapter(context, arrayList);
        //countryCodeAdapter = new CountryCodeAdapter(context, R.layout.spinner_country_code, arrayList);
        super.setAdapter(countryCodeAdapter);
    }

    public CountryCodeSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        fillCountryList();
        countryCodeAdapter = new CountryCodeAdapter(context, arrayList);
        //countryCodeAdapter = new CountryCodeAdapter(context, R.layout.spinner_country_code, arrayList);
        super.setAdapter(countryCodeAdapter);
    }

    private void fillCountryList() {
        //https://emojipedia.org/flags/
        arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add(new CountryModel("af", "Afghanistan", "+93", "🇦🇫"));
        arrayList.add(new CountryModel("al", "Albania", "+355", "🇦🇱"));
        arrayList.add(new CountryModel("dz", "Algeria", "+213", "🇩🇿"));
        arrayList.add(new CountryModel("as", "American Samoa", "+1684", "🇦🇸"));
        arrayList.add(new CountryModel("ad", "Andorra", "+376", "🇦🇩"));
        arrayList.add(new CountryModel("ao", "Angola", "+244", "🇦🇴"));
        arrayList.add(new CountryModel("ai", "Anguilla", "+1264", "🇦🇮"));
        arrayList.add(new CountryModel("aq", "Antarctica", "+672", "🇦🇶"));
        arrayList.add(new CountryModel("ag", "Antigua and Barbuda", "+1268", "🇦🇬"));
        arrayList.add(new CountryModel("ar", "Argentina", "+54", "🇦🇷"));
        arrayList.add(new CountryModel("am", "Armenia", "+374", "🇦🇲"));
        arrayList.add(new CountryModel("aw", "Aruba", "+297", "🇦🇼"));
        arrayList.add(new CountryModel("au", "Australia", "+61", "🇦🇺"));
        arrayList.add(new CountryModel("at", "Austria", "+43", "🇦🇹"));
        arrayList.add(new CountryModel("az", "Azerbaijan", "+994", "🇦🇿"));
        arrayList.add(new CountryModel("bs", "Bahamas", "+1242", "🇧🇸"));
        arrayList.add(new CountryModel("bh", "Bahrain", "+973", "🇧🇭"));
        arrayList.add(new CountryModel("bd", "Bangladesh", "+880", "🇧🇩"));
        arrayList.add(new CountryModel("bb", "Barbados", "+1242", "🇧🇧"));
        arrayList.add(new CountryModel("by", "Belarus", "+375", "🇧🇾"));
        arrayList.add(new CountryModel("be", "Belgium", "+32", "🇧🇪"));
        arrayList.add(new CountryModel("bz", "Belize", "+501", "🇧🇿"));
        arrayList.add(new CountryModel("bj", "Benin", "+229", "🇧🇯"));
        arrayList.add(new CountryModel("bm", "Bermuda", "+1441", "🇧🇲"));
        arrayList.add(new CountryModel("bt", "Bhutan", "+975", "🇧🇹"));
        arrayList.add(new CountryModel("bo", "Bolivia", "+591", "🇧🇴"));
        arrayList.add(new CountryModel("ba", "Bosnia And Herzegovina", "+387", "🇧🇦"));
        arrayList.add(new CountryModel("bw", "Botswana", "+267", "🇧🇼"));
        arrayList.add(new CountryModel("br", "Brazil", "+55", "🇧🇷"));
        arrayList.add(new CountryModel("io", "British Indian Ocean Territory", "+246", "🇮🇴"));
        arrayList.add(new CountryModel("vg", "British Virgin Islands", "+1284", "🇻🇬"));
        arrayList.add(new CountryModel("bn", "Brunei Darussalam", "+673", "🇧🇳"));
        arrayList.add(new CountryModel("bg", "Bulgaria", "+359", "🇧🇬"));
        arrayList.add(new CountryModel("bf", "Burkina Faso", "+226", "🇧🇫"));
        arrayList.add(new CountryModel("bi", "Burundi", "+257", "🇧🇮"));
        arrayList.add(new CountryModel("kh", "Cambodia", "+855", "🇰🇭"));
        arrayList.add(new CountryModel("cm", "Cameroon", "+237", "🇨🇲"));
        arrayList.add(new CountryModel("ca", "Canada", "+1", "🇨🇦"));
        arrayList.add(new CountryModel("cv", "Cape Verde", "+238", "🇨🇻"));
        arrayList.add(new CountryModel("ky", "Cayman Islands", "+345", "🇰🇾"));
        arrayList.add(new CountryModel("cf", "Central African Republic", "+236", "🇨🇫"));
        arrayList.add(new CountryModel("td", "Chad", "+235", "🇹🇩"));
        arrayList.add(new CountryModel("cl", "Chile", "+56", "🇨🇱"));
        arrayList.add(new CountryModel("cn", "China", "+86", "🇨🇳"));
        arrayList.add(new CountryModel("cx", "Christmas Island", "+61", "🇨🇽"));
        arrayList.add(new CountryModel("cc", "Cocos (keeling) Islands", "+61", "🇨🇨"));
        arrayList.add(new CountryModel("co", "Colombia", "+57", "🇨🇴"));
        arrayList.add(new CountryModel("km", "Comoros", "+269", "🇰🇲"));
        arrayList.add(new CountryModel("ck", "Cook Islands", "+682", "🇨🇰"));
        arrayList.add(new CountryModel("cr", "Costa Rica", "+506", "🇨🇷"));
        arrayList.add(new CountryModel("hr", "Croatia", "+385", "🇭🇷"));
        arrayList.add(new CountryModel("cu", "Cuba", "+53", "🇨🇺"));
        arrayList.add(new CountryModel("cy", "Cyprus", "+357", "🇨🇾"));
        arrayList.add(new CountryModel("cz", "Czech Republic", "+420", "🇨🇿"));
        arrayList.add(new CountryModel("ci", "Côte D'ivoire", "+225", "🇨🇮"));
        arrayList.add(new CountryModel("cd", "Democratic Republic of the Congo", "+243", "🇨🇩"));
        arrayList.add(new CountryModel("dk", "Denmark", "+45", "🇩🇰"));
        arrayList.add(new CountryModel("dj", "Djibouti", "+253", "🇩🇯"));
        arrayList.add(new CountryModel("dm", "Dominica", "+1767", "🇩🇲"));
        arrayList.add(new CountryModel("do", "Dominican Republic", "+1849", "🇩🇴"));
        arrayList.add(new CountryModel("ec", "Ecuador", "+593", "🇪🇨"));
        arrayList.add(new CountryModel("eg", "Egypt", "+20", "🇪🇬"));
        arrayList.add(new CountryModel("sv", "El Salvador", "+503", "🇸🇻"));
        arrayList.add(new CountryModel("gq", "Equatorial Guinea", "+240", "🇬🇶"));
        arrayList.add(new CountryModel("er", "Eritrea", "+291", "🇪🇷"));
        arrayList.add(new CountryModel("ee", "Estonia", "+372", "🇪🇪"));
        arrayList.add(new CountryModel("et", "Ethiopia", "+251", "🇪🇹"));
        arrayList.add(new CountryModel("fk", "Falkland Islands (malvinas)", "+500", "🇫🇰"));
        arrayList.add(new CountryModel("fo", "Faroe Islands", "+298", "🇫🇴"));
        arrayList.add(new CountryModel("fj", "Fiji", "+679", "🇫🇯"));
        arrayList.add(new CountryModel("fi", "Finland", "+358", "🇫🇮"));
        arrayList.add(new CountryModel("fr", "France", "+33", "🇫🇷"));
        arrayList.add(new CountryModel("gf", "French Guiana", "+594", "🇬🇫"));
        arrayList.add(new CountryModel("pf", "French Polynesia", "+689", "🇵🇫"));
        arrayList.add(new CountryModel("ga", "Gabon", "+241", "🇬🇦"));
        arrayList.add(new CountryModel("gm", "Gambia", "+220", "🇬🇲"));
        arrayList.add(new CountryModel("ge", "Georgia", "+995", "🇬🇪"));
        arrayList.add(new CountryModel("de", "Germany", "+49", "🇩🇪"));
        arrayList.add(new CountryModel("gh", "Ghana", "+233", "🇬🇭"));
        arrayList.add(new CountryModel("gi", "Gibraltar", "+350", "🇬🇮"));
        arrayList.add(new CountryModel("gr", "Greece", "+30", "🇬🇷"));
        arrayList.add(new CountryModel("gl", "Greenland", "+299", "🇬🇱"));
        arrayList.add(new CountryModel("gd", "Grenada", "+1473", "🇬🇩"));
        arrayList.add(new CountryModel("gp", "Guadeloupe", "+590", "🇬🇵"));
        arrayList.add(new CountryModel("gu", "Guam", "+1671", "🇬🇺"));
        arrayList.add(new CountryModel("gt", "Guatemala", "+502", "🇬🇹"));
        arrayList.add(new CountryModel("gg", "Guernsey", "+44", "🇬🇬"));
        arrayList.add(new CountryModel("gn", "Guinea", "+224", "🇬🇳"));
        arrayList.add(new CountryModel("gw", "Guinea-Bissau", "+245", "🇬🇼"));
        arrayList.add(new CountryModel("gy", "Guyana", "+592", "🇬🇾"));
        arrayList.add(new CountryModel("ht", "Haiti", "+509", "🇭🇹"));
        arrayList.add(new CountryModel("va", "Holy See (Vatican City State)", "+379", "🇻🇦"));
        arrayList.add(new CountryModel("hn", "Honduras", "+504", "🇭🇳"));
        arrayList.add(new CountryModel("hk", "Hong Kong", "+852", "🇭🇰"));
        arrayList.add(new CountryModel("hu", "Hungary", "+36", "🇭🇺"));
        arrayList.add(new CountryModel("is", "Iceland", "+354", "🇮🇸"));
        arrayList.add(new CountryModel("in", "India", "+91", "🇮🇳"));
        arrayList.add(new CountryModel("id", "Indonesia", "+62", "🇮🇩"));
        arrayList.add(new CountryModel("ir", "Iran", "+98", "🇮🇷"));
        arrayList.add(new CountryModel("iq", "Iraq", "+964", "🇮🇶"));
        arrayList.add(new CountryModel("ie", "Ireland", "+353", "🇮🇪"));
        arrayList.add(new CountryModel("im", "Isle Of Man", "+44", "🇮🇲"));
        arrayList.add(new CountryModel("il", "Israel", "+972", "🇮🇱"));
        arrayList.add(new CountryModel("it", "Italy", "+39", "🇮🇹"));
        arrayList.add(new CountryModel("jm", "Jamaica", "+1876", "🇯🇲"));
        arrayList.add(new CountryModel("jp", "Japan", "+81", "🇯🇵"));
        arrayList.add(new CountryModel("je", "Jersey", "+44", "🇯🇪"));
        arrayList.add(new CountryModel("jo", "Jordan", "+962", "🇯🇴"));
        arrayList.add(new CountryModel("kz", "Kazakhstan", "+7", "🇰🇿"));
        arrayList.add(new CountryModel("ke", "Kenya", "+254", "🇰🇪"));
        arrayList.add(new CountryModel("ki", "Kiribati", "+686", "🇰🇮"));
        arrayList.add(new CountryModel("xk", "Kosovo", "+383", "🇽🇰"));
        arrayList.add(new CountryModel("kw", "Kuwait", "+965", "🇰🇼"));
        arrayList.add(new CountryModel("kg", "Kyrgyzstan", "+996", "🇰🇬"));
        arrayList.add(new CountryModel("la", "Lao People's Democratic Republic", "+856", "🇱🇦"));
        arrayList.add(new CountryModel("lv", "Latvia", "+371", "🇱🇻"));
        arrayList.add(new CountryModel("lb", "Lebanon", "+961", "🇱🇧"));
        arrayList.add(new CountryModel("ls", "Lesotho", "+266", "🇱🇸"));
        arrayList.add(new CountryModel("lr", "Liberia", "+231", "🇱🇷"));
        arrayList.add(new CountryModel("ly", "Libya", "+218", "🇱🇾"));
        arrayList.add(new CountryModel("li", "Liechtenstein", "+423", "🇱🇮"));
        arrayList.add(new CountryModel("lt", "Lithuania", "+370", "🇱🇹"));
        arrayList.add(new CountryModel("lu", "Luxembourg", "+352", "🇱🇺"));
        arrayList.add(new CountryModel("mo", "Macao Sar China", "+853", "🇲🇴"));
        arrayList.add(new CountryModel("mk", "Macedonia", "+389", "🇲🇰"));
        arrayList.add(new CountryModel("mg", "Madagascar", "+261", "🇲🇬"));
        arrayList.add(new CountryModel("mw", "Malawi", "+265", "🇲🇼"));
        arrayList.add(new CountryModel("my", "Malaysia", "+60", "🇲🇾"));
        arrayList.add(new CountryModel("mv", "Maldives", "+960", "🇲🇻"));
        arrayList.add(new CountryModel("ml", "Mali", "+223", "🇲🇱"));
        arrayList.add(new CountryModel("mt", "Malta", "+356", "🇲🇹"));
        arrayList.add(new CountryModel("mh", "Marshall Islands", "+692", "🇲🇭"));
        arrayList.add(new CountryModel("mq", "Martinique", "+596", "🇲🇶"));
        arrayList.add(new CountryModel("mr", "Mauritania", "+222", "🇲🇷"));
        arrayList.add(new CountryModel("mu", "Mauritius", "+230", "🇲🇺"));
        arrayList.add(new CountryModel("yt", "Mayotte", "+262", "🇾🇹"));
        arrayList.add(new CountryModel("mx", "Mexico", "+52", "🇲🇽"));
        arrayList.add(new CountryModel("fm", "Micronesia", "+691", "🇫🇲"));
        arrayList.add(new CountryModel("md", "Moldova", "+373", "🇲🇩"));
        arrayList.add(new CountryModel("mc", "Monaco", "+377", "🇲🇨"));
        arrayList.add(new CountryModel("mn", "Mongolia", "+976", "🇲🇳"));
        arrayList.add(new CountryModel("me", "Montenegro", "+382", "🇲🇪"));
        arrayList.add(new CountryModel("ms", "Montserrat", "+1664", "🇲🇸"));
        arrayList.add(new CountryModel("ma", "Morocco", "+212", "🇲🇦"));
        arrayList.add(new CountryModel("mz", "Mozambique", "+258", "🇲🇿"));
        arrayList.add(new CountryModel("mm", "Myanmar (Burma)", "+95", "🇲🇲"));
        arrayList.add(new CountryModel("na", "Namibia", "+264", "🇳🇦"));
        arrayList.add(new CountryModel("nr", "Nauru", "+674", "🇳🇷"));
        arrayList.add(new CountryModel("np", "Nepal", "+977", "🇳🇵"));
        arrayList.add(new CountryModel("nl", "Netherlands", "+31", "🇳🇱"));
        arrayList.add(new CountryModel("nc", "New Caledonia", "+687", "🇳🇨"));
        arrayList.add(new CountryModel("nz", "New Zealand", "+64", "🇳🇿"));
        arrayList.add(new CountryModel("ni", "Nicaragua", "+505", "🇳🇮"));
        arrayList.add(new CountryModel("ne", "Niger", "+227", "🇳🇪"));
        arrayList.add(new CountryModel("ng", "Nigeria", "+234", "🇳🇬"));
        arrayList.add(new CountryModel("nu", "Niue", "+683", "🇳🇺"));
        arrayList.add(new CountryModel("nf", "Norfolk Island", "+1670", "🇳🇫"));
        arrayList.add(new CountryModel("kp", "North Korea", "+672", "🇰🇵"));
        arrayList.add(new CountryModel("mp", "Northern Mariana Islands", "+850", "🇲🇵"));
        arrayList.add(new CountryModel("no", "Norway", "+47", "🇳🇴"));
        arrayList.add(new CountryModel("om", "Oman", "+968", "🇴🇲"));
        arrayList.add(new CountryModel("pk", "Pakistan", "+92", "🇵🇰"));
        arrayList.add(new CountryModel("pw", "Palau", "+680", "🇵🇼"));
        arrayList.add(new CountryModel("ps", "Palestinian Territory, Occupied", "+970", "🇵🇸"));
        arrayList.add(new CountryModel("pa", "Panama", "+507", "🇵🇦"));
        arrayList.add(new CountryModel("pg", "Papua New Guinea", "+675", "🇵🇬"));
        arrayList.add(new CountryModel("py", "Paraguay", "+595", "🇵🇾"));
        arrayList.add(new CountryModel("pe", "Peru", "+51", "🇵🇪"));
        arrayList.add(new CountryModel("ph", "Philippines", "+63", "🇵🇭"));
        arrayList.add(new CountryModel("pn", "Pitcairn Islands", "+870", "🇵🇳"));
        arrayList.add(new CountryModel("pl", "Poland", "+48", "🇵🇱"));
        arrayList.add(new CountryModel("pt", "Portugal", "+351", "🇵🇹"));
        arrayList.add(new CountryModel("pr", "Puerto Rico", "+1939", "🇵🇷"));
        arrayList.add(new CountryModel("qa", "Qatar", "+974", "🇶🇦"));
        arrayList.add(new CountryModel("cg", "Republic of the Congo - Brazzaville", "+242", "🇨🇬"));
        arrayList.add(new CountryModel("ro", "Romania", "+40", "🇷🇴"));
        arrayList.add(new CountryModel("ru", "Russian Federation", "+7", "🇷🇺"));
        arrayList.add(new CountryModel("rw", "Rwanda", "+250", "🇷🇼"));
        arrayList.add(new CountryModel("re", "Réunion", "+262", "🇷🇪"));
        arrayList.add(new CountryModel("bl", "Saint Barthélemy", "+590", "🇧🇱"));
        arrayList.add(new CountryModel("sh", "Saint Helena", "+290", "🇸🇭"));
        arrayList.add(new CountryModel("kn", "Saint Kitts & Nevis", "+1869", "🇰🇳"));
        arrayList.add(new CountryModel("lc", "Saint Lucia", "+1758", "🇱🇨"));
        arrayList.add(new CountryModel("mf", "Saint Martin", "+590", "🇲🇫"));
        arrayList.add(new CountryModel("pm", "Saint Pierre & Miquelon", "+508", "🇵🇲"));
        arrayList.add(new CountryModel("vc", "Saint Vincent & The Grenadines", "+1784", "🇻🇨"));
        arrayList.add(new CountryModel("ws", "Samoa", "+685", "🇼🇸"));
        arrayList.add(new CountryModel("sm", "San Marino", "+378", "🇸🇲"));
        arrayList.add(new CountryModel("st", "Sao Tome & Principe", "+239", "🇸🇹"));
        arrayList.add(new CountryModel("sa", "Saudi Arabia", "+966", "🇸🇦"));
        arrayList.add(new CountryModel("sn", "Senegal", "+221", "🇸🇳"));
        arrayList.add(new CountryModel("rs", "Serbia", "+381", "🇷🇸"));
        arrayList.add(new CountryModel("sc", "Seychelles", "+248", "🇸🇨"));
        arrayList.add(new CountryModel("sl", "Sierra Leone", "+232", "🇸🇱"));
        arrayList.add(new CountryModel("sg", "Singapore", "+65", "🇸🇬"));
        arrayList.add(new CountryModel("sx", "Sint Maarten", "+1", "🇸🇽"));
        arrayList.add(new CountryModel("sk", "Slovakia", "+421", "🇸🇰"));
        arrayList.add(new CountryModel("si", "Slovenia", "+386", "🇸🇮"));
        arrayList.add(new CountryModel("sb", "Solomon Islands", "+677", "🇸🇧"));
        arrayList.add(new CountryModel("so", "Somalia", "+252", "🇸🇴"));
        arrayList.add(new CountryModel("za", "South Africa", "+27", "🇿🇦"));
        arrayList.add(new CountryModel("gs", "South Africa (South Georgia & South Sandwich Islands)", "+500", "🇬🇸"));
        arrayList.add(new CountryModel("kr", "South Korea", "+82", "🇰🇷"));
        arrayList.add(new CountryModel("ss", "South Sudan", "+211", "🇸🇸"));
        arrayList.add(new CountryModel("es", "Spain", "+34", "🇪🇸"));
        arrayList.add(new CountryModel("lk", "Sri Lanka", "+94", "🇱🇰"));
        arrayList.add(new CountryModel("sd", "Sudan", "+249", "🇸🇩"));
        arrayList.add(new CountryModel("sr", "Suriname", "+597", "🇸🇷"));
        arrayList.add(new CountryModel("sz", "Swaziland", "+268", "🇸🇿"));
        arrayList.add(new CountryModel("se", "Sweden", "+46", "🇸🇪"));
        arrayList.add(new CountryModel("ch", "Switzerland", "+41", "🇨🇭"));
        arrayList.add(new CountryModel("sy", "Syrian Arab Republic", "+963", "🇸🇾"));
        arrayList.add(new CountryModel("tw", "Taiwan", "+886", "🇹🇼"));
        arrayList.add(new CountryModel("tj", "Tajikistan", "+992", "🇹🇯"));
        arrayList.add(new CountryModel("tz", "Tanzania", "+255", "🇹🇿"));
        arrayList.add(new CountryModel("th", "Thailand", "+66", "🇹🇭"));
        arrayList.add(new CountryModel("tl", "Timor-Leste", "+670", "🇹🇱"));
        arrayList.add(new CountryModel("tg", "Togo", "+228", "🇹🇬"));
        arrayList.add(new CountryModel("tk", "Tokelau", "+690", "🇹🇰"));
        arrayList.add(new CountryModel("to", "Tonga", "+676", "🇹🇴"));
        arrayList.add(new CountryModel("tt", "Trinidad & Tobago", "+1868", "🇹🇹"));
        arrayList.add(new CountryModel("tn", "Tunisia", "+216", "🇹🇳"));
        arrayList.add(new CountryModel("tr", "Turkey", "+90", "🇹🇷"));
        arrayList.add(new CountryModel("tm", "Turkmenistan", "+993", "🇹🇲"));
        arrayList.add(new CountryModel("tc", "Turks & Caicos Islands", "+1649", "🇹🇨"));
        arrayList.add(new CountryModel("tv", "Tuvalu", "+688", "🇹🇻"));
        arrayList.add(new CountryModel("ug", "Uganda", "+256", "🇺🇬"));
        arrayList.add(new CountryModel("ua", "Ukraine", "+380", "🇺🇦"));
        arrayList.add(new CountryModel("ae", "United Arab Emirates", "+971", "🇦🇪"));
        arrayList.add(new CountryModel("gb", "United Kingdom", "+44", "🇬🇧"));
        arrayList.add(new CountryModel("us", "United States", "+1", "🇺🇸"));
        arrayList.add(new CountryModel("uy", "Uruguay", "+598", "🇺🇾"));
        arrayList.add(new CountryModel("vi", "US Virgin Islands", "+1340", "🇻🇮"));
        arrayList.add(new CountryModel("uz", "Uzbekistan", "+998", "🇺🇿"));
        arrayList.add(new CountryModel("vu", "Vanuatu", "+678", "🇻🇺"));
        arrayList.add(new CountryModel("ve", "Venezuela", "+58", "🇻🇪"));
        arrayList.add(new CountryModel("vn", "Vietnam", "+84", "🇻🇳"));
        arrayList.add(new CountryModel("wf", "Wallis And Futuna", "+681", "🇼🇫"));
        arrayList.add(new CountryModel("ye", "Yemen", "+967", "🇾🇪"));
        arrayList.add(new CountryModel("zm", "Zambia", "+260", "🇿🇲"));
        arrayList.add(new CountryModel("zw", "Zimbabwe", "+263", "🇿🇼"));
        arrayList.add(new CountryModel("ax", "Åland Islands", "+358", "🇦🇽"));

        Collections.sort(arrayList, CountryModel.IsoComparator);  //sort by ISO
    }

    public static class CountryModel {

        private String iso, phoneCode, countryName, countryFlag;

        public CountryModel(String iso, String countryName, String phoneCode, String countryFlag) {
            this.iso = iso;
            this.countryName = countryName;
            this.phoneCode = phoneCode;
            this.countryFlag = countryFlag;
        }

        public String getIso() {
            return iso;
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryFlag() {
            return countryFlag;
        }

        public void setCountryFlag(String countryFlag) {
            this.countryFlag = countryFlag;
        }

        /*Comparator for sorting the list by phoneCode(prefix)*/
        public static Comparator<CountryModel> phoneCodeComparator = new Comparator<CountryModel>() {

            public int compare(CountryModel c1, CountryModel c2) {
                String pref1 = c1.getPhoneCode();
                String pref2 = c2.getPhoneCode();

                //ascending order
                return pref1.compareTo(pref2);
            }
        };

        /*Comparator for sorting the list by ISO (country code)*/
        public static Comparator<CountryModel> IsoComparator = new Comparator<CountryModel>() {

            public int compare(CountryModel c1, CountryModel c2) {
                String iso1 = c1.getIso();
                String iso2 = c2.getIso();

                //ascending order
                return iso1.compareTo(iso2);
            }
        };
    }

    public class CountryCodeAdapter extends ArrayAdapter<CountryModel> {
        private LayoutInflater inflater;
        private Context context;
        private ArrayList<CountryModel> arrayList;
        private int resource;

        public CountryCodeAdapter(@NonNull Context context, ArrayList<CountryModel> arrayList) {
            super(context, 0, arrayList);
            this.inflater = LayoutInflater.from(context);
            this.context = context;
            this.arrayList = arrayList;
        }

        public CountryCodeAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<CountryModel> arrayList) {
            super(context, resource, 0, arrayList);
            this.inflater = LayoutInflater.from(context);
            this.context = context;
            this.arrayList = arrayList;
            this.resource = resource;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        @Override
        public @NonNull
        View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        private View createItemView(int position, View convertView, ViewGroup parent) {

            final View view;

            CountryModel model = arrayList.get(position);

            if (resource == 0) {
                LinearLayoutCompat rootLayout = new LinearLayoutCompat(context);
                rootLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                rootLayout.setBackgroundColor(Color.WHITE);

                AppCompatTextView ccTextView = new AppCompatTextView(context);
                ccTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                ccTextView.setBackgroundColor(Color.WHITE);
                ccTextView.setTextColor(Color.BLACK);
                ccTextView.setPadding(10, 5, 10, 5);
                ccTextView.setTextSize(15);
                ccTextView.setText(model.getCountryFlag() + " " + model.getIso().toUpperCase() +" (" + model.getPhoneCode() + ")");
                rootLayout.addView(ccTextView);
                view = rootLayout;
            } else {
                view = inflater.inflate(resource, parent, false);
                AppCompatTextView tvCountry = (AppCompatTextView) view.findViewById(R.id.tvCountry);
                tvCountry.setText(model.getCountryFlag() + " " + model.getIso().toUpperCase() + " (" + model.getPhoneCode() + ")");
            }

            return view;
        }
    }
}
