# ScratchScripts
## Unpacking a scratch project:
- inside a scratch project press File -> Save to your computer which should download a .sb3 file
- rename the .sb3 extension to .zip
- extract the newly renamed file like you would any other .zip file
- inside, you should see a json file, which contains variable, list and block data from your scratch project, and is what these programs use to load data into scratch
## Uploading it back to scratch: 
- After unpacking the .sb3 file, you have to re-compress it in order to upload it back to scratch. 
- select all the files that were origonally unpacked from the .zip file along with your modified .json file 
- compress them to a zip file
- rename the .zip extension to .sb3
- open a new scratch project and inside, press File -> Load from your computer
- upload your newly created .sb3 file 
- if all goes well, the scratch project will load up and you will have some cool new things in your project. 

## [Model3dScratchLoader:](https://github.com/trrt-good/ScratchScripts/blob/master/Model3dScratchLoader.java)
  a script for loading 3d model files (only .obj) into scratch json files. 
### Info:
- The program reads the .obj file provided in the variable `MODEL_3D_NAME`
- Next the program reads the json file provided in the variable `JSON_FILE_NAME`
- The program then loads all the data from the .obj file into 9 lists (defined in `LIST_NAMES`) (x, y, z)*3 for all 3 vertices of the triangles which should be found in the json file. 
### Important note: 
the scratch project's 3d triangle data lists must be in the following order for the triangles to look right: 
- x1 list
- y1 list
- x2 list
- y2 list
- z1 list
- z2 list
- z3 list
