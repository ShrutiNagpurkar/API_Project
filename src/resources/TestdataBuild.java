package resources;

import java.util.ArrayList;
import java.util.List;

import serializationtest.AddPlace;
import serializationtest.Location;

public class TestdataBuild {

	public AddPlace addPlacePayLoad()
	{
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_num("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setName("Frontline house");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		Location l = new Location();
		l.setLat(-38.98764);
		l.setLng(33.427362);
		p.setLocation(l);
		return p;
	}
	
	//drive data with different set of datas
	public AddPlace addPlacePayLoad(String string, String string1, String string2)
	{
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress(string2);
		p.setLanguage(string1);
		p.setPhone_num("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setName(string);
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		Location l = new Location();
		l.setLat(-38.98764);
		l.setLng(33.427362);
		p.setLocation(l);
		return p;
	}
	
	public String deletePlacePayload(String placeID)
	{
		return "{\r\n\"place_id\":\""+placeID+"\"\r\n}";
	}
}
