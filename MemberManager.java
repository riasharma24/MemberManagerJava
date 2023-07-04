import java.io.*;
public class MemberManager
{
private static String DATA_FILE="members.dat";
public static void main(String gg[])
{
if(gg.length==0)
{
System.out.println("Invalid Number of arguments.");
return;
}
String operation=gg[0];
if(!isOperationValid(operation))
{
System.out.println("Invalid operation.");
return;
}
if(operation.equalsIgnoreCase("add"))
{
add(gg);
}
else if(operation.equalsIgnoreCase("update"))
{
update(gg);
}
else if(operation.equalsIgnoreCase("getAll"))
{
getAll(gg);
}
else if(operation.equalsIgnoreCase("remove"))
{
remove(gg);
}
else if(operation.equalsIgnoreCase("getByContactNumber"))
{
getByContactNumber(gg);
}
else if(operation.equalsIgnoreCase("getAll"))
{
getAll(gg);
}
}


//operator functions
private static void add(String[] data)
{
if(data.length!=5)
{
System.out.println("Not enough data.");
return;
}
String contactNumber=data[1];
String name=data[2];
String course=data[3];
if(!isCourseValid(course))
{
System.out.println("Invalid course");
return;
}
int fee;
try{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException nfe)
{
System.out.println("Fee should be an integer value.");
return;
}
try{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
String fMobileNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
if(fMobileNumber.equalsIgnoreCase(contactNumber))
{
System.out.println("The provided mobile number already exists.");
randomAccessFile.close();
return;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
randomAccessFile.writeBytes(contactNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(name);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(course);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(fee));
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
System.out.println("Member added.");
return;
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
return;
}
}
private static void update(String[] data)
{
if(data.length!=5)
{
System.out.println("Invalid number of arguments.");
return;
}
String contactNumber = data[1];
String name=data[2];
String course=data[3];
int fee=0;
try{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException nfe)
{
System.out.println("Fee should be an integer.");
return;
}
try{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("No entries.");
return;
}
if(file.length()==0)
{
System.out.println("No entries.");
return;
}
String fContactNumber;
String fName;
String fCourse;
int fFee;
boolean flag=false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
if(fContactNumber.equalsIgnoreCase(contactNumber))
{
flag=true;
break;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
if(flag==false)
{
System.out.println("Invalid details.");
return;
}
System.out.println("Updating the information of "+name+".");
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile, "rw");
tmpRandomAccessFile.setLength(0);
randomAccessFile.seek(0);
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(fContactNumber.equalsIgnoreCase(contactNumber))
{
tmpRandomAccessFile.writeBytes(contactNumber+"\n");
tmpRandomAccessFile.writeBytes(name+"\n");
tmpRandomAccessFile.writeBytes(course+"\n");
tmpRandomAccessFile.writeBytes(String.valueOf(fee)+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(fContactNumber+"\n");
tmpRandomAccessFile.writeBytes(fName+"\n");
tmpRandomAccessFile.writeBytes(fCourse+"\n");
tmpRandomAccessFile.writeBytes(String.valueOf(fFee)+"\n");
}
}
randomAccessFile.seek(0);
randomAccessFile.setLength(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
System.out.println("Information Updated.");
return;
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}



}

private static void remove(String[] data)
{
if(data.length!=2)
{
System.out.println("Invalid Number of arguments.");
return;
}
String contactNumber=data[1];
try{
File file=new File(DATA_FILE);
if(file==null)
{
System.out.println("No entries");
return;
}
if(file.length()==0)
{
System.out.println("No entries");
return;
}
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
String fContactNumber;
String fName;
String fCourse;
String fFee;
boolean flag=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
if(fContactNumber.equalsIgnoreCase(contactNumber))
{
flag=true;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
if(flag==false)
{
System.out.println("Invalid contact number.");
return;
}
System.out.println("Deleting the contact.");
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.setLength(0);
randomAccessFile.seek(0);
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=randomAccessFile.readLine();
if(contactNumber.equalsIgnoreCase(fContactNumber)==false)
{
tmpRandomAccessFile.writeBytes(fContactNumber+"\n");
tmpRandomAccessFile.writeBytes(fName+"\n");
tmpRandomAccessFile.writeBytes(fCourse+"\n");
tmpRandomAccessFile.writeBytes(fFee+"\n");
}
else{
continue;
}
}
randomAccessFile.setLength(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
System.out.println("Details of "+contactNumber+" deleted.");
return;
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}

private static void getByContactNumber(String[] data)
{
if(data.length!=2)
{
System.out.println("Invalid number of arguments.");
return;
}
String contactNumber=data[1];
try{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
String fMobileNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
if(fMobileNumber.equalsIgnoreCase(contactNumber))
{
System.out.println("Contact Number : "+fMobileNumber);
System.out.println("Name : "+(randomAccessFile.readLine()));
System.out.println("Course : "+(randomAccessFile.readLine()));
return;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
randomAccessFile.close();
System.out.println("Invalid contact number.");
return;
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
return;
}
}

private static void getAll(String[] data)
{
if(data.length!=1)
{
System.out.println("Invalid Number of arguments.");
return;
}
try{
File file=new File(DATA_FILE);
if(file==null)
{
System.out.println("No entries.");
return;
}
if(file.length()==0)
{
System.out.println("No entries.");
return;
}
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
String fCourse;
String fName;
String fContactNumber;
String fFee;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=randomAccessFile.readLine();
System.out.println("Name : "+fName);
System.out.println("Contact Number : "+fContactNumber);
System.out.println("Course : "+fCourse);
System.out.println("Fee : "+fFee);
}
randomAccessFile.close();
return;
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}


//helper functions

private static boolean isCourseValid(String course)
{
String courses[]={"C","C++","Java","Python"};
for(int x=0;x<courses.length;x++)
{
if(courses[x].equalsIgnoreCase(course)) return true;
}
return false;
}


private static boolean isOperationValid(String operation)
{
String operations[]={"add","update","remove","getAll","getByContactNumber"};
for(int x=0;x<operations.length;x++)
{
if(operation.equalsIgnoreCase(operations[x])) return true;
}
return false;
}




}