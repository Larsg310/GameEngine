package modules.terrain;

import core.texturing.Texture2D;
import core.utils.Util;
import modules.gpgpu.NormalMapRenderer;

import java.io.BufferedReader;
import java.io.FileReader;

public class TerrainConfig
{
    private float scaleY;
    private float scaleXZ;
    
    private Texture2D heightMap;
    private Texture2D normalMap;
    
    private int tessellationFactor;
    private float tessellationSlope;
    private float tessellationShift;
    
    private int[] lod_range = new int[8];
    private int[] lod_morphing_area = new int[8];
    
    public void loadFile(String file)
    {
        BufferedReader reader;
        
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null)
            {
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);
                
                if (tokens.length == 0) continue;
                if (tokens[0].equals("scaleY")) setScaleY(Float.valueOf(tokens[1]));
                if (tokens[0].equals("scaleXZ")) setScaleXZ(Float.valueOf(tokens[1]));
                if (tokens[0].equals("heightmap")) {
                    setHeightMap(new Texture2D(tokens[1]));
                    getHeightMap().bind();
                    getHeightMap().bilinearFilter();
    
                    NormalMapRenderer normalMapRenderer = new NormalMapRenderer(getHeightMap().getWidth());
                    normalMapRenderer.setStrength(4);
                    normalMapRenderer.render(getHeightMap());
                    setNormalMap(normalMapRenderer.getNormalmap());
                };
                if (tokens[0].equals("tessellationFactor")) setTessellationFactor(Integer.valueOf(tokens[1]));
                if (tokens[0].equals("tessellationSlope")) setTessellationSlope(Float.valueOf(tokens[1]));
                if (tokens[0].equals("tessellationShift")) setTessellationShift(Float.valueOf(tokens[1]));
                if (tokens[0].equals("#lod_ranges"))
                {
                    for (int i = 0; i < 8; i++)
                    {
                        line = reader.readLine();
                        tokens = line.split(" ");
                        tokens = Util.removeEmptyStrings(tokens);
                        if (tokens[0].equals("lod" + (i + 1) + "_range"))
                        {
                            if (Integer.valueOf(tokens[1]) == 0)
                            {
                                lod_range[i] = 0;
                                lod_morphing_area[i] = 0;
                            }
                            else setLodRange(i, Integer.valueOf(tokens[1]));
                        }
                    }
                }
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private int updateMorphingArea(int lod)
    {
        return (int) ((scaleXZ / TerrainQuadTree.ROOT_NODES) / Math.pow(2, lod));
    }
    
    public float getScaleY()
    {
        return scaleY;
    }
    
    public void setScaleY(float scaleY)
    {
        this.scaleY = scaleY;
    }
    
    public float getScaleXZ()
    {
        return scaleXZ;
    }
    
    public void setScaleXZ(float scaleXZ)
    {
        this.scaleXZ = scaleXZ;
    }
    
    public void setLodRange(int index, int lod_range)
    {
        this.lod_range[index] = lod_range;
        lod_morphing_area[index] = lod_range - updateMorphingArea(index + 1);
    }
    
    public int getLodMorphingArea(int lod)
    {
        return lod_morphing_area[lod];
    }
    
    public int getLodRange(int lod)
    {
        return lod_range[lod];
    }
    
    public int getTessellationFactor()
    {
        return tessellationFactor;
    }
    
    public void setTessellationFactor(int tessellationFactor)
    {
        this.tessellationFactor = tessellationFactor;
    }
    
    public float getTessellationSlope()
    {
        return tessellationSlope;
    }
    
    public void setTessellationSlope(float tessellationSlope)
    {
        this.tessellationSlope = tessellationSlope;
    }
    
    public float getTessellationShift()
    {
        return tessellationShift;
    }
    
    public void setTessellationShift(float tessellationShift)
    {
        this.tessellationShift = tessellationShift;
    }
    
    public Texture2D getHeightMap()
    {
        return heightMap;
    }
    
    public void setHeightMap(Texture2D heightMap)
    {
        this.heightMap = heightMap;
    }
    
    public Texture2D getNormalMap()
    {
        return normalMap;
    }
    
    public void setNormalMap(Texture2D normalMap)
    {
        this.normalMap = normalMap;
    }
    
}
