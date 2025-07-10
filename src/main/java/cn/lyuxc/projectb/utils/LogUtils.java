package cn.lyuxc.projectb.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LogUtils {
    public static List<String> mapListToString(List<Map<String, Object>> list){
        List<String> result=new ArrayList<>();
        list.forEach(map -> {
           StringBuilder sb = new StringBuilder();
           AtomicInteger i = new AtomicInteger(map.size());
           sb.append("{");
           map.forEach((k,v)->{
               i.getAndDecrement();
               sb.append("\"").append(k).append("\"");
               sb.append(":");
               sb.append("\"").append(v.toString()).append("\"");
               if (i.get() != 0) {
                   sb.append(",");
               }
           });
            sb.append("}");
           result.add(sb.toString());
        });
        return result;
    }
}
