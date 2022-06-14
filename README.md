# Creating a Graphic Image
### by David Berger and Ariel Zuckermann - at Lev Academic Institute
Our Program produces realistic images (exporting in .png format) from Algebraically defined Shapes. We color the objects based on the intersection of their points and directional vectors of light sources. The .png file is compiled by printing the pixels of the Camera's View Plane.

### What we learned from this project:
We implemented different design patterns, like **Builder** (see “Scene”, “Camera”), **Composite** (“Geometries”), **Wrapper Class/functions** (“Color” and Java’s awt.color).  
We coordinated sophisticated Linear Algebra and Physics to **create different effects**: partial lighting and shadows, transparency, rotating the camera, glossy/blurry surfaces, etc.  
We learned about Runtime efficiency - by running multiple threads simoultaneously, and using **Boundary Volume Hierarchy.**   
We wrote **tests** before any implementation (“TDD”) using **Junit**, and learned about **Unit vs Integration testing**, **BVA and ECP** (Boundary Value Analysis and Equivalent Class Partitioning.  

## Some Images and Effects:
### Blurry Effect (see bottom right half):
![image](https://user-images.githubusercontent.com/91850832/173515638-aca0d395-c032-4825-88ac-fa4c457d86d7.png)

## Partial shadows:
![image](https://user-images.githubusercontent.com/91850832/173515301-4c558dbd-73bd-4746-b10f-3ef3d0231001.png)

### Bounding Volume Hierarchy:
(normally, the boxes are invisibile....)
![image](https://user-images.githubusercontent.com/91850832/173515407-0b368052-4fb1-4003-97b7-ba74a6f224c1.png)

### Reflections:
![image](https://user-images.githubusercontent.com/91850832/173515748-6035b8fe-f4a2-4668-a061-3741111ea367.png)

# Diagrams Referenced in Comments on Source Code:
(all diagrams taken from Dr. Elishai Ezra Tsur and Dan Ziblerstein - from Lev Academic Institute)

## Diagrams for Ray Tracing - Intersections
### Diagram 1.1 - Intersection of Ray and Sphere
![image](https://user-images.githubusercontent.com/91850832/159281281-15e89cb6-2558-4bf0-b36d-32e2ce15a943.png)
### Diagram 1.2 - Intersection of Ray and Plane
![image](https://user-images.githubusercontent.com/92029043/159908587-649c250d-e077-469c-8651-2dca974b884a.png)
### Diagram 1.3 - Intersection of Ray and Triangle
![image](https://user-images.githubusercontent.com/92029043/159909319-91e35666-a66d-4fbc-b211-f2284c34d83d.png)


## Diagrams for Ray Construction thru View Plane with Camera
### Diagram 2.2 - Position of Camera and View Plane
![image](https://user-images.githubusercontent.com/91850832/160981229-5ae04570-a83a-4751-8b3c-03cd47db47af.png)
### Diagram 2.3 - Formula to construct ray thru View Plane
![image](https://user-images.githubusercontent.com/91850832/160821202-5832f13e-146d-4e84-8cce-d9ba42a7c018.png)


## Diagrams for Light Refraction
### Diagram 3.1 - Phong Reflection Model
![image](https://user-images.githubusercontent.com/91850832/166256268-64c030f3-36e9-48fc-8a48-c7eedce55d44.png)
### Diagram 3.2 - Calculating Vectors "r" and "l" 
![image](https://user-images.githubusercontent.com/91850832/167423317-a165b981-50ca-43c8-8267-3378e2aa07ed.png)
### Diagram 3.3 - Light Attenuation with Distance
![image](https://user-images.githubusercontent.com/91850832/167838447-e3cdd10e-c49a-4215-a49e-eebf9b0ce4d2.png)
### Diagram 3.4 - Constructing a Reflection Ray
![image](https://user-images.githubusercontent.com/91850832/168073335-3a3b1fc3-62dd-45b0-8d49-a702c07e1c7e.png)

