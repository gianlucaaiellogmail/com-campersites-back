package old.info.campersites.tools;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	
	public final static String KILOMETRI = "K";
	public final static String MIGLIA = "M";
	public final static String NAUTICI = "N";
	
	public static String md5(String input) {
		String res = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(input.getBytes());
			byte[] md5 = algorithm.digest();
			String tmp = "";
			for (int i = 0; i < md5.length; i++) {
				tmp = (Integer.toHexString(0xFF & md5[i]));
				if (tmp.length() == 1) {
					res += "0" + tmp;
				} else {
					res += tmp;
				}
			}
		} catch (NoSuchAlgorithmException ex) {}
		return res;
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String capitalizeFirstLetterInEverySentence(String content) {
		if (content == null || content.isEmpty()) return content;
	    Pattern capitalize = Pattern.compile("([\\?!\\.]\\s*)([a-z\u00E0-\u00FF])");
	    Matcher m = capitalize.matcher(content);
	    while (m.find()) {
	        content = m.replaceFirst(m.group(1) + m.group(2).toUpperCase());
	        m = capitalize.matcher(content);
	    }
	    // Capitalize the first letter of the string.
	    content = String.format("%s%s", Character.toUpperCase(content.charAt(0)), content.substring(1));
	    return content;
	}

	public static String capitalizeFirstLetterInEveryWord(String content) {
		if (content == null || content.isEmpty()) return content;
		StringBuffer stringbf = new StringBuffer();
	    Matcher m = Pattern.compile("([a-z\u00E0-\u00FF])([a-z\u00E0-\u00FF]*)", Pattern.CASE_INSENSITIVE).matcher(content);
	    while (m.find()) {
	       m.appendReplacement(stringbf, m.group(1).toUpperCase() + m.group(2).toLowerCase());
	    }
	    return m.appendTail(stringbf).toString();
	}

    public static double distance(float lat1, float lon1, float lat2, float lon2, String unit) {
    	float theta = lon1 - lon2;
    	double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (KILOMETRI.equals(unit)) {
        	dist = dist * 1.609344;
        } else if (NAUTICI.equals(unit)) {
        	dist = dist * 0.8684;
        }
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

	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
	    int originalWidth = imgSize.width;
	    int originalHeight = imgSize.height;
	    int boundWidth = boundary.width;
	    int boundHeight = boundary.height;
	    int newWidth = originalWidth;
	    int newHeight = originalHeight;
	    // first check if we need to scale width
	    if (originalWidth > boundWidth) {
	        //scale width to fit
	        newWidth = boundWidth;
	        //scale height to maintain aspect ratio
	        newHeight = (newWidth * originalHeight) / originalWidth;
	    }
	    // then check if we need to scale even with the new height
	    if (newHeight > boundHeight) {
	        //scale height to fit instead
	        newHeight = boundHeight;
	        //scale width to maintain aspect ratio
	        newWidth = (newHeight * originalWidth) / originalHeight;
	    }
	    return new Dimension(newWidth, newHeight);
	}
	
	public static BufferedImage resizeImageWithHint(BufferedImage originalImage, Dimension boundary, int type){
		BufferedImage resizedImage = new BufferedImage((int)boundary.getWidth(), (int)boundary.getHeight(), type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, (int)boundary.getWidth(), (int)boundary.getHeight(), null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		return resizedImage;
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

	public static String rpHash(String value) {
		int hash = 5381;
		value = value.toUpperCase();
		for(int i = 0; i < value.length(); i++) {
			hash = ((hash << 5) + hash) + value.charAt(i);
		}
		return String.valueOf(hash);
	}

	public static boolean isValidFloat(String floatString) {
		try {
			if (floatString != null && !floatString.isEmpty()) {
				Float.parseFloat(floatString);
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean urlFormatCheck(String url) {
		if (url != null && !url.isEmpty()) {
			try {
				new URL(url);
			} catch (MalformedURLException e) {
				return false;
			}
		}
		return true;
	}

	public static boolean chiusuraFormatCheck(String chiusura) {
		if (chiusura != null && !chiusura.isEmpty() && !chiusura.matches("[0-3][0-9]/[0-1][0-9]-[0-3][0-9]/[0-1][0-9]")) {
			return false;
		}
		return true;
	}

	public static Integer getIntegerValue(String value) {
		Integer result = null;
		if (value != null && !value.isEmpty()) {
			result = Integer.parseInt(value);
		}
		return result;
	}

	public static Float getFloatValue(String value) {
		Float result = null;
		if (value != null && !value.isEmpty()) {
			result = Float.parseFloat(value);
		}
		return result;
	}

}
