package misskey.com.pictruemanager.utils;

import java.util.HashMap;

/**
 * 保存缩略图的绝对路径
 * Created by misskey on 15-6-4.
 */
public class ThumbnailsUtil {
    private static HashMap<Integer, String> hash = new HashMap<>();

    /**
     * 返回value
     * @param key
     * @param defalut
     * @return
     */

    public static String MapgetHashValue(int key,String defalut){
        if(hash==null||!hash.containsKey(key)) return defalut;
        return hash.get(key);
    }
    public static void putValue(Integer key,String value){
        hash.put(key,value);

    }
    public static void clear(){
        hash.clear();
    }
}

