package com.barclays.bmg.location;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("restriction")
public class POIDetails extends Location {

    private String type;

    private String name;

    private String address;

    private String phoneNumber;

    private String fax;

    private String customerHelpline;

    private String imageFileName;

    private List<String> services;

    private POIOpeningHours openingHours;

    @XmlElement
    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    @XmlElement
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @XmlElement
    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    @XmlElement
    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    @XmlElement
    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    @XmlElement
    public String getCustomerHelpline() {
	return customerHelpline;
    }

    public void setCustomerHelpline(String customerHelpline) {
	this.customerHelpline = customerHelpline;
    }

    @XmlElementWrapper(name = "services")
    @XmlElements( { @XmlElement(name = "service", type = String.class) })
    public List<String> getServices() {
	return services;
    }

    public void setServices(List<String> services) {
	this.services = services;
    }

    public void addService(String service) {
	this.services.add(service);
    }

    @XmlElement
    public POIOpeningHours getOpeningHours() {
	return openingHours;
    }

    public void setOpeningHours(POIOpeningHours openingHours) {
	this.openingHours = openingHours;
    }

    @XmlElement
    public String getImageFileName() {
	return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
	this.imageFileName = imageFileName;
    }

}
