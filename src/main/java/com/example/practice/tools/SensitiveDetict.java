package com.example.practice.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PriNode {
    char content;
    Map<String, PriNode> map = new HashMap<>();
    boolean isLastNode = false;
    boolean isKey = false;

    public PriNode() {
        isLastNode = false;
        isKey = false;
    }
}

public class SensitiveDetict {
    StringBuffer stringBuffer = new StringBuffer();
    String text = "***";
    String sensi = "赌博";
    private static PriNode root = new PriNode();
    private static PriNode firstNode = root;
    private static int  position = 0;
    private static int loopPosion = 0;
    public static PriNode constractPriTree(List<String> sensitiveStringList) {
        PriNode newPriNode =null;
        for (String sensitiveString : sensitiveStringList) {
            /*一个字符串遍历到最后要是下层已经存在当前字符直接移动firstnode就行*/
            for (int i = 0; i < sensitiveString.length(); i++) {
                newPriNode=new PriNode();
                char charnow = sensitiveString.charAt(i);
                if (firstNode.map.get("" + charnow) != null) {
                    firstNode = firstNode.map.get("" + charnow);
                    continue;
                }
                //下面一层没有相应的

                newPriNode.content = sensitiveString.charAt(i);
                if (i == sensitiveString.length() - 1) {
                    newPriNode.isKey = true;
                    newPriNode.isLastNode = true;
                }
                newPriNode=firstNode;
                firstNode.map.put("" + sensitiveString.charAt(i),newPriNode);
                if (i == sensitiveString.length() - 1) {
                    firstNode = root;
                }
                firstNode = newPriNode;
            }
        }
        return firstNode;
    }





    public static String sensitiveDetict(String content) {

        StringBuffer str=new StringBuffer();
        PriNode loopPriNode=null;
        for(int i=position;i<content.length();i++){
            //下层不含有关键字  position+1    把position位置 加入buffer
            loopPosion=position;
            if(firstNode.map.get(""+content.charAt(i))==null){
                str.append(content.charAt(i));
                position++;
                if(position==content.length()){
                    return str.toString();
                }
            }
            //循环下层含有关键字first移到下层的位置，loopposition后移看是否下层是否含有
            //直到下层不含有或则到达str末尾
            while (firstNode.map.get(""+content.charAt(i))!=null&&loopPosion<content.length()){



            }



        }






       return "";
    }




    public static void main(String args[]){
        List<String> stringList = new ArrayList<>();
        stringList.add("赌博");
        stringList.add("微信");
        PriNode testnode=constractPriTree(stringList);
        System.out.println(testnode.map.toString());


    }

}
