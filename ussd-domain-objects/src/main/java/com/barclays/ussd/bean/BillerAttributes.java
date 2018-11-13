package com.barclays.ussd.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerAttributes {
    @JsonProperty
    List<BillerArea> areaList;

    public List<BillerArea> getAreaList() {
	return areaList;
    }

    public void setAreaList(List<BillerArea> areaList) {
	this.areaList = areaList;
    }

    // public static void main(String args[]) {
    // BillerAttributes attr = new BillerAttributes();
    // List<BillerArea> areaList = new ArrayList<BillerArea>();
    // BillerArea area = null;
    //
    // area = new BillerArea();
    // area.setAreaName("Kampala");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Jinja");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Entebbe");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Iganga");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Lugazi");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Mukono");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Kajjansi");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Kawuku");
    // areaList.add(area);
    //
    // area = new BillerArea();
    // area.setAreaName("Others");
    // areaList.add(area);
    //
    // attr.setAreaList(areaList);
    //
    // String jsonResponse = getJSONResponse(attr);
    // System.out.println(jsonResponse);
    // }
    //
    // private static String getJSONResponse(Object obj) {
    // ObjectMapper mapper = new ObjectMapper();
    // String jsonString = "";
    // try {
    // jsonString = mapper.writeValueAsString(obj);
    // } catch (JsonGenerationException e) {
    // // TODO Auto-generated catch block
    // 
    // } catch (JsonMappingException e) {
    // // TODO Auto-generated catch block
    // 
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // 
    // }
    // return jsonString;
    // }
}
