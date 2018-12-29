package io.github.yedaxia.apidocs.parser;

import io.github.yedaxia.apidocs.Utils;

import java.util.*;

/**
 * class node
 *
 * @author yeguozhong yedaxia.github.com
 */
public class ClassNode {

    private String className = "";
    private String description;
    private Boolean isList = Boolean.FALSE;
    private List<FieldNode> childNodes = new ArrayList<>();
    private List<GenericNode> genericNodes = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isList() {
        return isList;
    }

    public void setList(Boolean list) {
        isList = list;
    }

    public List<FieldNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<FieldNode> childNodes) {
        this.childNodes = childNodes;
    }

    public void addChildNode(FieldNode fieldNode){
        childNodes.add(fieldNode);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<GenericNode> getGenericNodes() {
        return genericNodes;
    }

    public void setGenericNodes(List<GenericNode> genericNodes) {
        this.genericNodes = genericNodes;
    }

    public void addGenericNode(GenericNode genericNode){
        this.genericNodes.add(genericNode);
    }

    public GenericNode getGenericNode(int index){
        return genericNodes.get(index);
    }

    public GenericNode getGenericNode(String  type){
        for(GenericNode genericNode : genericNodes){
            if(genericNode.getPlaceholder().equals(type)){
                return genericNode;
            }
        }
        return null;
    }

    public String toJsonApi(){
        if(childNodes == null || childNodes.isEmpty()){
            return isList? className + "[]": className + "{}";
        }
        Map<String, Object> jsonRootMap = new LinkedHashMap<>();
        for (FieldNode recordNode : childNodes) {
            toJsonApiMap(recordNode,jsonRootMap);
        }
        if(isList){
            return Utils.toPrettyJson(new Map[]{jsonRootMap});
        }else{
            return Utils.toPrettyJson(jsonRootMap);
        }
    }

    // 特殊错误
    public String toJsonApi4Itacasa(){

        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("errorMessage","string //错误信息");

        if(childNodes == null || childNodes.isEmpty()){
            String json = isList? className + "[]": className + "{}";
            if (isList) {
                map.put("content", new Map[]{});

            } else {
                map.put("content", new Object());
            }

        }else {
            Map<String, Object> jsonRootMap = new LinkedHashMap<>();
            for (FieldNode recordNode : childNodes) {
                toJsonApiMap(recordNode, jsonRootMap);
            }

            if (isList) {
                map.put("content", new Map[]{jsonRootMap});

            } else {
                map.put("content", jsonRootMap);
            }
        }
       return Utils.toPrettyJson(map);
    }

    public void toJsonApiMap(FieldNode recordNode, Map<String, Object> map){
        ClassNode childResponseNode = recordNode.getChildResponseNode();
        if(childResponseNode != null){
            Map<String, Object> childMap = new LinkedHashMap<>();
            for (FieldNode childNode : childResponseNode.getChildNodes()) {
                if(childNode.getChildResponseNode() != null){
                    toJsonApiMap(childNode,childMap);
                }else{
                    childMap.put(Utils.string2SnakeCase(childNode.getName()) , getRecordDescp(childNode));
                }
            }
            if(recordNode.getType().endsWith("[]")){
                map.put(Utils.string2SnakeCase(recordNode.getName()), childMap.isEmpty()? new Map[]{}: new Map[]{childMap});
            }else{
                map.put(Utils.string2SnakeCase(recordNode.getName()), childMap);
            }
        }else{
            map.put(Utils.string2SnakeCase(recordNode.getName()), getRecordDescp(recordNode));
        }
    }

    private String getRecordDescp(FieldNode recordNode){
        if(Utils.isNotEmpty(recordNode.getDescription())){
            return String.format("%s //%s", recordNode.getType(),recordNode.getDescription());
        }else{
            return recordNode.getType();
        }
    }

}
