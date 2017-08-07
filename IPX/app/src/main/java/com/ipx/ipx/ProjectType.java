package com.ipx.ipx;

/**
 * 项目类型枚举
 */
public enum ProjectType {
    Apartment("公寓", 1),
    House("独栋别墅", 2),
    Townhouse("联排别墅", 3),
    Land("土地", 4);

    private String name;
    private int code;

    ProjectType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static String getNameByCode(int code){
        for(ProjectType projectType: ProjectType.values()){
            if(projectType.code == code) {
                return projectType.name;
            }
        }
        throw new RuntimeException("没有找到" + code + "对应的项目类型");
    }
}
