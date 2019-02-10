package info.campersites.utils;

import java.util.Calendar;
import java.util.Date;

public class Tools {
	
    public static double distance(float lat1, float lon1, float lat2, float lon2) {
    	float theta = lon1 - lon2;
    	double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
       	dist = dist * 1.609344; // Kilometers
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(float deg) {
    	return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double dist) {
    	return (dist * 180.0 / Math.PI);
    }

    public static String decimalToDMS(Float coord) {
    	if (coord == null) return null;
    	
    	String output, degrees, minutes, seconds;
    	double mod = coord % 1;
    	int intPart = (int)(float)coord;
    	
    	degrees = String.valueOf(intPart);
    	coord = (float) (mod * 60);
    	mod = coord % 1;
    	intPart = (int)(float)coord;
    	if (intPart < 0) {
    		intPart *= -1; // Convert number to positive if it's negative.
    	}
    	
    	minutes = String.valueOf(intPart);
    	coord = (float) (mod * 60);
    	intPart = (int)(float)coord;
    	if (intPart < 0) {
    		intPart *= -1; // Convert number to positive if it's negative.
    	}
    	
    	seconds = String.valueOf(intPart);
    	//output = degrees + "/1," + minutes + "/1," + seconds + "/1";
    	//Standard output of D°M′S″
    	output = degrees + "°" + minutes + "'" + seconds + "\"";
    	return output;
    }
  
	/*
	 * Conversion DMS to decimal
	 * 
	 * Input: latitude or longitude in the DMS format ( example: N 43° 36'
	 * 15.894") Return: latitude or longitude in decimal format
	 * hemisphereOUmeridien => {W,E,S,N}
	 */
	public static float DMSToDecimal(String hemisphereOUmeridien, double degres, double minutes, double secondes) {
		float LatOrLon = 0;
		float signe = (float) 1.0;
		if ((hemisphereOUmeridien.equals("W")) || (hemisphereOUmeridien.equals("S"))) {
			signe = (float) -1.0;
		}
		LatOrLon = (float) (signe * (Math.floor(degres) + Math.floor(minutes) / 60.0 + secondes / 3600.0));
		return (LatOrLon);
	}

	// chiusura GG/MM-GG/MM (FROM-TO)
	public static boolean checkChiusura(Date filtro, String chiusura) {
		Calendar filtroScelto = Calendar.getInstance();
		filtroScelto.setTime(filtro);
		
		Calendar chiusoFrom = Calendar.getInstance();
		chiusoFrom.set(Calendar.DAY_OF_MONTH, Integer.parseInt(chiusura.substring(0, 2)));
		chiusoFrom.set(Calendar.MONTH, Integer.parseInt(chiusura.substring(3, 5)));
		chiusoFrom.set(Calendar.YEAR, filtroScelto.get(Calendar.YEAR));
		
		Calendar chiusoTo = Calendar.getInstance();
		chiusoTo.set(Calendar.DAY_OF_MONTH, Integer.parseInt(chiusura.substring(6, 8)));
		chiusoTo.set(Calendar.MONTH, Integer.parseInt(chiusura.substring(9, 11)));
		chiusoTo.set(Calendar.YEAR, filtroScelto.get(Calendar.YEAR));
		
		if (chiusoFrom.after(chiusoTo)) chiusoFrom.add(Calendar.YEAR, -1);
		
		while (filtroScelto.after(chiusoTo)) {
			chiusoFrom.add(Calendar.YEAR, 1);
			chiusoTo.add(Calendar.YEAR, 1);
		}
		
		if (filtroScelto.after(chiusoFrom) && filtroScelto.before(chiusoTo)) {
			return true;
		}

		return false;
	}

    public static String encodeUrl(String url) {
    	return url
    	.replace("$", "")
    	.replace("&", "")
    	.replace("+", "")
    	.replace(",", "")
    	.replace("/", "")
    	.replace(":", "")
    	.replace(";", "")
    	.replace("=", "")
    	.replace("?", "")
    	.replace("@", "")
    	.replace("'", "")
    	.replace(" ", "_");
    }

}
