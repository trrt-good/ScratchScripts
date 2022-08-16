import java.util.*;
import java.io.*;
import java.awt.*;
public class ObjFileJsonLoader {

    public static final String MODEL_3D_NAME = "cube.obj";
    public static final double MDOEL_SCALE = 1;

    public static final String JSON_FILE_NAME = "project.json";

    public static final String[] LIST_NAMES = new String[]
    { //make sure these are in the same order that they appear in the json
        "x1 list",
        "y1 list",
        "x2 list",
        "y2 list",
        "x3 list",
        "y3 list",
        "z1 list",
        "z2 list",
        "z3 list",
    };

    public static ArrayList<Double> x1List = new ArrayList<Double>();
    public static ArrayList<Double> y1List = new ArrayList<Double>();
    public static ArrayList<Double> x2List = new ArrayList<Double>();
    public static ArrayList<Double> y2List = new ArrayList<Double>();
    public static ArrayList<Double> x3List = new ArrayList<Double>();
    public static ArrayList<Double> y3List = new ArrayList<Double>();
    public static ArrayList<Double> z1List = new ArrayList<Double>();
    public static ArrayList<Double> z2List = new ArrayList<Double>();
    public static ArrayList<Double> z3List = new ArrayList<Double>();

    public static void main(String[] args) throws Exception
    {
        readObjFile(new File(MODEL_3D_NAME), MDOEL_SCALE);
        writeJsonFile(new File(JSON_FILE_NAME));
    }

    public static String makeHex()
    {   
        String code = "";
        Random rng = new Random();
        for (int i = 0; i < 20; i ++)
        {
            code = code + (char)(rng.nextInt(93) + 33);
        }
        return code;
    }

    public static void writeJsonFile(File file) throws Exception
    {
        ArrayList<String> firstPart = new ArrayList<String>();
        ArrayList<String> hashes = new ArrayList<String>();
        Scanner scanner = null;
        PrintWriter writer = null;
        scanner = new Scanner(file);

        scanner.useDelimiter("\"");
        String word = "";
        while (scanner.hasNext())
        {
            word = scanner.next();
            firstPart.add(String.format("%s\"", word));
            if (word.equals(LIST_NAMES[LIST_NAMES.length-1]))
                break;
        }
        scanner.useDelimiter("],");
        firstPart.add("]," + scanner.next());
        String theRest = scanner.next();
        scanner.useDelimiter(" ");
        while (scanner.hasNext())
        {
            theRest += scanner.next();
        }
        scanner.close();

        writer = new PrintWriter(file);

        int size = firstPart.size()-1;
        for (int i = size; i >= 0; i --)
        {
            if (firstPart.get(i).startsWith(LIST_NAMES[0]))
                break;

            if (firstPart.get(i).length() == 21)
                hashes.add(firstPart.get(i));
            firstPart.remove(i);
        }
        firstPart.add(composeJsonList(hashes));

        for (int i = 0; i < firstPart.size(); i ++)
        {
            writer.print(firstPart.get(i));
        }

        writer.print(theRest);

        writer.close();
        scanner.close();
    }

    public static String composeJsonList(ArrayList<String> hashes)
    {
        StringBuilder stringBuilder = new StringBuilder(",[");
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", x1List.get(i)));
        stringBuilder.append(String.format("\"%f\"", x1List.get(x1List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(7) + ":[\"" + LIST_NAMES[1] + "\",[");
        
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", y1List.get(i)));
        stringBuilder.append(String.format("\"%f\"", y1List.get(y1List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(6) + ":[\"" + LIST_NAMES[2] + "\",[");
        
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", x2List.get(i)));
        stringBuilder.append(String.format("\"%f\"", x2List.get(x2List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(5) + ":[\"" + LIST_NAMES[3] + "\",[");
        
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", y2List.get(i)));
        stringBuilder.append(String.format("\"%f\"", y2List.get(y2List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(4) + ":[\"" + LIST_NAMES[4] + "\",[");
        
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", x3List.get(i)));
        stringBuilder.append(String.format("\"%f\"", x3List.get(x3List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(3) + ":[\"" + LIST_NAMES[5] + "\",[");
        
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", y3List.get(i)));
        stringBuilder.append(String.format("\"%f\"", y3List.get(y3List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(2) + ":[\"" + LIST_NAMES[6] + "\",[");
        
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", z1List.get(i)));
        stringBuilder.append(String.format("\"%f\"", z1List.get(z1List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(1) + ":[\"" + LIST_NAMES[7] + "\",[");

        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", z2List.get(i)));
        stringBuilder.append(String.format("\"%f\"", z2List.get(z2List.size()-1)));
        stringBuilder.append("]],\"" + hashes.get(0) + ":[\"" + LIST_NAMES[8] + "\",[");
        
        for (int i = 0; i < x1List.size()-1; i ++)
            stringBuilder.append(String.format("\"%f\",", z3List.get(i)));
        stringBuilder.append(String.format("\"%f\"", z3List.get(z3List.size()-1)));
        stringBuilder.append("]],");
        return stringBuilder.toString();
    }

    public static void readObjFile(File file, double scale)
    {
        ArrayList<Vector3> vertices = new ArrayList<Vector3>();
        ArrayList<Vector2> textureCoords = new ArrayList<Vector2>();

        Scanner scanner = null;
        String line = "";

        //innitialize the scanner
        try 
        {
            scanner = new Scanner(file);   
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("ERROR opening scanner");
            System.exit(1);
        }

        //scanner goes through the file
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();

            if (!line.equals(""))
            {
                //v means Vector3 in .obj files
                if (line.startsWith("v "))
                {
                    StringTokenizer lineTokens = new StringTokenizer(line);
                    lineTokens.nextToken();
                    //create the Vector3 object

                    Vector3 vertexCoordinate = new Vector3(Double.parseDouble(lineTokens.nextToken()), Double.parseDouble(lineTokens.nextToken()), Double.parseDouble(lineTokens.nextToken()));

                    //adds the Vector3 to the array of vertices
                    vertices.add(vertexCoordinate);
                }

                //vt means Vector3 texture coordinates.
                if (line.startsWith("vt "))
                {
                    StringTokenizer tokens = new StringTokenizer(line);
                    tokens.nextToken();
                    textureCoords.add(new Vector2(Double.parseDouble(tokens.nextToken()), Double.parseDouble(tokens.nextToken())));
                }

                //f means face in .obj files
                if (line.startsWith("f "))
                {
                    StringTokenizer lineTokens = new StringTokenizer(line);
                    lineTokens.nextToken();
                    int tokenLength = lineTokens.countTokens();
                    int[] coordinateIndexes = new int[tokenLength];
                    int[] textureIndexes = new int[tokenLength]; 
                    String[] tempArr;

                    Color color = Color.LIGHT_GRAY;
                    for (int i = 0; i < tokenLength; i ++)
                    {
                        tempArr = lineTokens.nextToken().split("/");
                        coordinateIndexes[i] = Integer.parseInt(tempArr[0])-1;
                        textureIndexes[i] = Integer.parseInt(tempArr[1])-1;
                    }
                    
                    //create triangles based on the indicated verticies. However often verticies are not in sets of 3, so create multiple triangles if necessary.
                    for (int i = 0; i < coordinateIndexes.length - 2; i ++)
                    {   
                        x1List.add(vertices.get(coordinateIndexes[0]).x);
                        y1List.add(vertices.get(coordinateIndexes[0]).x);
                        z1List.add(vertices.get(coordinateIndexes[0]).x);

                        x2List.add(vertices.get(coordinateIndexes[i+1]).y);
                        y2List.add(vertices.get(coordinateIndexes[i+1]).y);
                        z2List.add(vertices.get(coordinateIndexes[i+1]).y);

                        x3List.add(vertices.get(coordinateIndexes[i+2]).z);
                        y3List.add(vertices.get(coordinateIndexes[i+2]).z);
                        z3List.add(vertices.get(coordinateIndexes[i+2]).z);
                        // textureCoords.get(textureIndexes[0]),
                        // textureCoords.get(textureIndexes[i+1]),
                        // textureCoords.get(textureIndexes[i+2])
                    }
                }
            }
        }
        scanner.close();
    }
}

class Vector3 
{
    public double x;
    public double y;
    public double z;

    public Vector3(double xIn, double yIn, double zIn)
    {
        x = xIn;
        y = yIn;
        z = zIn;
    }
}

class Vector2 
{
    public final double x;
    public final double y;

    public Vector2 (double xIn, double yIn)
    {
        x = xIn;
        y = yIn;
    }
}
