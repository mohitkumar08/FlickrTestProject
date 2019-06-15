package com.bit.flickertestproject;

public class Temp {
    public static void main(String ar[]){
        String string="(\"thomas.cote\")";
        String pq=string.replaceFirst("\"","");
        String mm=string.replace("(","").replace(")","").replaceAll("\"","");
        System.out.println(mm);

        String pqr=string.replaceAll("[( | ) | \" ]", "");

        System.out.println(pqr);


        string.replaceAll("^\"|\"$", "");
    }
}
