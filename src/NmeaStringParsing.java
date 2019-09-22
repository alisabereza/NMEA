import java.text.ParseException;

public class NmeaStringParsing {
    public static void NmeaParser(String string) throws ParseException {
        String[] subStr;
        String delimiter = ",";
        subStr = string.split(delimiter);
        subStr[0] = subStr[0].substring(1);
        subStr[subStr.length - 1] = subStr[subStr.length - 1].substring(0, subStr[subStr.length - 1].indexOf("*"));


        switch (subStr[0]) {
            case "GPGSV":
            case "GLGSV": {
                Gpgsv(subStr);
                break;
            }
            case "GNGLL":
            case "GPGLL": {
                Gngll(subStr);
                break;
            }
            case "GNRMC":
            case "GPRMC": {
                Gnrmc(subStr);
                break;
            }
            case "GNVTG":
            case "GPVTG": {
                Gnvtg(subStr);
                break;
            }
            case "GNGGA":
            case "GPGGA": {
                Gngga(subStr);
                break;
            }
            case "GNGSA":
            case "GPGSA": {
                Gngsa(subStr);
                break;
            }
            default: {
                System.out.println("Unsupported type");
            }
        }

    }

    public static int stringToInt(String s) {
        int number = s.equals("") ? 0 : Integer.parseInt(s);
        return number;
    }

    public static Double stringToDouble(String s) {
        double number = s.equals("") ? 0.0 : Double.parseDouble(s);
        return number;
    }

    public static void stringToTime(String s) {
        if (s.equals("")) {
            System.out.println("HH: - MM: - SS: - MS: -");
        } else {
            String time;
            if (s.length() == 6) {
                time = s + ".000";
            } else time = s;
            String hh = time.substring(0, 2);
            String mm = time.substring(2, 4);
            String ss = time.substring(4, 6);
            String ms = time.substring(7);

            System.out.println("HH: " + Integer.parseInt(hh) + " MM: " + Integer.parseInt(mm) + " SS: " + Integer.parseInt(ss) + " MS: " + Integer.parseInt(ms));
        }
    }

    public static void stringToDate(String s) {
        if (s.equals("")) {
            System.out.println("DD: - MM: - YY: - ");
        } else {
            String dd = s.substring(0, 2);
            String mm = s.substring(2, 4);
            String yy = s.substring(4);
            System.out.println("DD: " + Integer.parseInt(dd) + " MM: " + Integer.parseInt(mm) + " YY: " + (2000 + Integer.parseInt(yy)));
        }
    }

    public static char stringToChar(String s) {
        char s1 = s.equals("") ? '-' : s.charAt(0);
        return s1;
    }

    public static void Gpgsv(String[] str) {
        String[] fields = {
                "NMEA Title                                             ",
                "Total number of messages of this type in this cycle    ",
                "Message number                                         ",
                "Total number of SVs in view                            ",
                "SV PRN number                                       ",
                "Elevation in degrees, 90 maximum                    ",
                "Azimuth, degrees from true north, 000 to 359        ",
                "SNR, 00-99 dB (null when not tracking               "};


        System.out.println("NMEA Title: " + str[0]);
        for (int i = 1; i < 4; i++) {
            System.out.println(fields[i] + ": " + stringToInt(str[i]));
        }
        for (int i = 0; i < (str.length - 4) / 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println((i + 1) + "SV " + fields[j + 4] + ": " + stringToInt(str[j + 4 * (i + 1)]));
            }
        }
    }

    public static void Gngll(String[] str) throws ParseException {
        String[] fields = {
                "NMEA Title                                                           ",
                "Latitude (DDmm.mm) + direction (N = North, S = South)                ",
                "Longitude (DDDmm.mm) + direction (E = East, W = West)                ",
                "UTC time status of position (hours/minutes/seconds/decimal seconds)  ",
                "Data status: A = Data valid, V = Data invalid                        ",
                "Positioning system mode indicator                                    "};
        System.out.println(fields[0] + ": " + str[0]);
        System.out.println(fields[1] + ": " + stringToDouble(str[1]) + ", " + stringToChar(str[2]));
        System.out.println(fields[2] + ": " + stringToDouble(str[3]) + ", " + stringToChar(str[4]));
        System.out.print(fields[3] + ": ");
        stringToTime(str[5]);
        System.out.println(fields[4] + ": " + stringToChar(str[6]));
        if (str.length == 8) {
            System.out.println(fields[5] + ": " + stringToChar(str[str.length - 1]));
        }
    }

    public static void Gnrmc(String[] str) throws ParseException {

        String[] fields = {
                "NMEA Title                   ",
                "Time Stamp                   ",
                "Validity - A-ok, V-invalid   ",
                "Current Latitude, North/South",
                "Current Longitude, East/West ",
                "Speed in knots               ",
                "True course                  ",
                "Date Stamp                   ",
                "Magnetic variation, East/West"};

        System.out.println(fields[0] + ": " + str[0]);
        System.out.print(fields[1] + ": ");
        stringToTime(str[1]);
        System.out.println(fields[2] + ": " + (str[2].equals("") ? "" : str[2].charAt(0)));
        System.out.println(fields[3] + ": " + stringToDouble(str[3]) + ", " + stringToChar(str[4]));
        System.out.println(fields[4] + ": " + stringToDouble(str[5]) + ", " + stringToChar(str[6]));
        System.out.println(fields[5] + ": " + stringToDouble(str[7]));
        System.out.println(fields[6] + ": " + stringToDouble(str[8]));
        System.out.print(fields[7] + ": " );
        stringToDate(str[9]);
        System.out.println(fields[8] + ": " + stringToDouble(str[10]) + ", " + stringToChar(str[11]));

    }

    public static void Gnvtg(String[] str) {

        String[] fields = {
                "NMEA Title                        ",
                "True track made good              ",
                "Magnetic track made good          ",
                "Ground speed, knots               ",
                "Ground speed, Kilometers per hour "};
        System.out.println(fields[0] + ": " + str[0]);
        for (int i = 1; i < fields.length; i++) {
            System.out.println(fields[i] + ": " + stringToDouble(str[2 * i - 1]) + ", " + stringToChar(str[2 * i]));
        }

    }

    public static void Gngga(String[] str) throws ParseException {
        String[] fields = {
                "NMEA Title                                                  ",
                "Time                                                        ",
                "Latitude                                                    ",
                "Longitude                                                   ",
                "GPS Quality indicator (0=no fix, 1=GPS fix, 2=Dif. GPS fix) ",
                "Number of Satellites                                        ",
                "Horizontal Dilution of Precision (HDOP)                     ",
                "Altitude:                                                   ",
                "Height of geoid above WGS84 ellipsoid                       ",
                "Time since last DGPS update                                 ",
                "DGPS reference station id                                   "};
        System.out.println(fields[0] + ": " + str[0]);
        System.out.print(fields[1] + ": ");
        stringToTime(str[1]);
        System.out.println(fields[2] + ": " + stringToDouble(str[2]) + ", " + stringToChar(str[3]));
        System.out.println(fields[3] + ": " + stringToDouble(str[4]) + ", " + stringToChar(str[5]));
        System.out.println(fields[4] + ": " + stringToInt(str[6]));
        System.out.println(fields[5] + ": " + stringToInt(str[7]));
        System.out.println(fields[6] + ": " + stringToDouble(str[8]));
        System.out.println(fields[7] + ": " + stringToDouble(str[9]) + ", " + stringToChar(str[10]));
        System.out.println(fields[8] + ": " + stringToDouble(str[11]) + ", " + stringToChar(str[12]));
        System.out.print(fields[9] + ": ");
        stringToTime(str[13]);
        System.out.println(fields[10] + ": " + stringToInt(str[14]));
    }

    public static void Gngsa(String[] str) {
        String[] fields = {
                "NMEA Title                                                         ",
                "Mode (M=Manual, forced to operate in 2D or 3D, A=Automatic, 3D/2D) ",
                "Mode (1=Fix not available, 2=2D, 3=3D)                             ",
                "ID of SV used in position fix (null for unused fields)             ",
                "PDOP                                                               ",
                "HDOP                                                               ",
                "VDOP                                                               "};

        System.out.println(fields[0] + ": " + str[0]);
        System.out.println(fields[1] + ": " + stringToChar(str[1]));
        System.out.println(fields[2] + ": " + stringToInt(str[2]));

        for (int i = 3; i <= 14; i++) {
            System.out.println(fields[3] + ": " + stringToInt(str[i]));
        }
        for (int i = 15; i < 18; i++) {
            System.out.println(fields[i - 11] + ": " + stringToDouble(str[i]));
        }
    }

    public static void main(String[] args) throws ParseException {
        NmeaParser("$GNRMC,165111.000,A,5601.0318,N,01211.3503,E,0.08,43.53,190706,,*3E");
    }
}
