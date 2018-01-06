package com.shoppingcartdemo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/11/30.
 */

public class SignUtils {
    private static String TAG = SignUtils.class.getSimpleName();

    public static String get_rand_number(Map<String, String> keyMap, String rand_number) {
        if (keyMap.isEmpty()) return "0";
        String stringMD5 = MD5Utils.getStringMD5(rand_number);
        String substring = stringMD5.substring(stringMD5.length() - 1, stringMD5.length());
        LogUtils.d(TAG, "请求传过来的值substring=>" + substring);
        return keyMap.get(substring);
    }

    public static String get_sign(Map<String, String> map, String mrand_number, String sign_time) {
        Map<String, String> keyMap = getKeyMap();
        String tmp_rand_number = get_rand_number(keyMap, mrand_number);
        //value=map的原始值+随机数+临时随机数保存在map的中
        for (Map.Entry<String, String> map_ : keyMap.entrySet()) {
            long value = Integer.parseInt(map_.getValue()) + Long.parseLong(mrand_number) + Integer.parseInt(tmp_rand_number);
            keyMap.put(map_.getKey(), String.valueOf(value));
        }

        String link_string = create_link_string(map);
        LogUtils.d(TAG, "请求传过来的值link_string=>" + link_string);
        String md5_string = MD5Utils.getStringMD5(link_string);
        LogUtils.d(TAG, "请求传过来的值md5_string=>" + md5_string);
        LogUtils.d(TAG, "请求传过来的值md5_sign_time=>" + sign_time);
        int sign = 0;
        for (int i = 0; i < 32; i++) {
            String map_key = keyMap.get(String.valueOf(md5_string.charAt(i)));
            int map_value = Integer.parseInt(map_key);
            sign += map_value;

        }
        sign = sign + Integer.parseInt(sign_time);
        LogUtils.d(TAG, "请求传过来的值md5_sign=" + sign);
        return MD5Utils.getStringMD5(String.valueOf(sign));
    }


    private static String create_link_string(Map<String, String> map) {
        List<String> keyList = new ArrayList<>();
        if (map.isEmpty()) return "";
        for (Map.Entry<String, String> mapEntry : map.entrySet()) {

            if ("[".equals(mapEntry.getValue())) {
                map.remove(mapEntry.getKey());
            } else {
                keyList.add(mapEntry.getKey());
            }

        }
        Collections.sort(keyList);
        String link_string = "";
        for (int i = 0; i < keyList.size(); i++) {


            link_string += keyList.get(i) + "=" + map.get(keyList.get(i)) + "&";
        }

        String linkStr = link_string.substring(0, link_string.length() - 1);

        return linkStr;

    }


    public static int getRandom(int min, int max) {
        Random random = new Random();
        int num = random.nextInt(max) % (max - min + 1) + min;
        return num;
    }


    /**
     * 20170704  更新
     */
    private static Map<String, String> getKeyMap() {

        /**
         * 1'=>'235',
         '2'=>'254',
         '3'=>'253',
         '4'=>'252',
         '5'=>'251',
         '6'=>'250',
         '7'=>'249',
         '8'=>'248',
         '9'=>'247',
         '0'=>'923',
         'a'=>'245',
         'b'=>'244',
         'c'=>'243',
         'd'=>'242',
         'e'=>'241',
         'f'=>'240',
         'g'=>'239',
         'h'=>'565',
         'i'=>'237',
         'j'=>'553',
         'k'=>'235',
         'l'=>'234',
         'm'=>'199',
         'n'=>'232',
         'o'=>'231',
         'p'=>'230',
         'q'=>'229',
         'r'=>'228',
         's'=>'227',
         't'=>'226',
         'u'=>'225',
         'v'=>'224',
         'w'=>'333',
         'x'=>'213',
         'y'=>'221',
         'z'=>'220'
         */
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("1", "234");
        keyMap.put("2", "254");
        keyMap.put("3", "253");
        keyMap.put("4", "252");
        keyMap.put("5", "251");
        keyMap.put("6", "250");
        keyMap.put("7", "249");
        keyMap.put("8", "248");
        keyMap.put("9", "247");
        keyMap.put("0", "923");
        keyMap.put("a", "245");
        keyMap.put("b", "244");
        keyMap.put("c", "243");
        keyMap.put("d", "242");
        keyMap.put("e", "894");
        keyMap.put("f", "240");
        keyMap.put("g", "239");
        keyMap.put("h", "565");
        keyMap.put("i", "237");
        keyMap.put("j", "553");
        keyMap.put("k", "572");
        keyMap.put("l", "234");
        keyMap.put("m", "199");
        keyMap.put("n", "232");
        keyMap.put("o", "231");
        keyMap.put("p", "230");
        keyMap.put("q", "229");
        keyMap.put("r", "228");
        keyMap.put("s", "227");
        keyMap.put("t", "226");
        keyMap.put("u", "225");
        keyMap.put("v", "224");
        keyMap.put("w", "333");
        keyMap.put("x", "943");
        keyMap.put("y", "221");
        keyMap.put("z", "758");
        LogUtils.d(TAG, "key=" + keyMap.toString());

        return keyMap;
    }
}
