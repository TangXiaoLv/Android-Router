
package com.tangxiaolv.compiler;

class PUtils {

    static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    // "List<String>,List<String>,Map<String,String>" => "List.class,List.class"
    static String getFullTypesString(String agrsTypes) {
        if (agrsTypes.contains(",")) {
            String[] arr = agrsTypes.split(",");
            StringBuilder builder = new StringBuilder();
            boolean mapAppear = false;
            for (String s : arr) {
                if (mapAppear) {
                    mapAppear = false;
                    continue;
                }
                if (s.contains("Map"))
                    mapAppear = true;
                builder.append(removeGeneric(s)).append(".class,");

            }
            String result = builder.toString();
            return result.substring(0, result.length() - 1);
        } else {
            return removeGeneric(agrsTypes) + ".class";
        }
    }

    // "List<String>" => "List"
    private static String removeGeneric(String argType) {
        int index = argType.indexOf("<");
        if (index != -1) {
            argType = argType.substring(0, index);
        }
        return argType;
    }
}
