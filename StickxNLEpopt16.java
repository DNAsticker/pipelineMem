import java.lang.Integer;

public class StickxNLEpopt16{
//   <title>Extended DNA Sticker System in JavaScript</title>

//globals needed for NLE
//note:  capital S3 and S5 to avoid naming conflict with Stickx
static boolean s0,s1,s2,S3,s4,S5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15;
static boolean c0,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15;
static boolean z1,z2,z3,z4,z5,z6,z7,z8,z9,z10,z11,z12,z13,z14;
static boolean f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15;
static int aw, Mt, Mts, M, temp, PCIR;

//****sticker modified to have tubePop (except staple) 5/27/18
//int current_read_pos = 0;
//boolean read_safely = true;
static boolean produce_listing = true;
static int l = 2; //form.user_l.value;
static int k = 4; //form.user_k.value;
static int numTubes = 4;//form.user_numTubes.value;
static String user_fmt = "2d1b1b";

static String alpha = "abcdefghijklmnopqrstuvwxyz";
static int numStrands = 0;
static int maxStrands = 270000;
static String[] s3 = new String[maxStrands]; 
static String[] s5 = new String[maxStrands]; 
static int[] tubeNum = new int[maxStrands];  
static int[] tubePop = new int[maxStrands]; // 5/27/18  faster, but not staple



public static void mywriteln(String s) 
{
  System.out.println(s);
}

public static void myerr(String s) 
{
  mywriteln(s);
}


public static void mywrite(String s) {
  System.out.print(s);
}

// simulated Pascal strings

public static String mycopy(String s, int posit, int len) 
{ //System.out.println("posit="+posit+" len="+len+" s="+s);
  if (posit < 1 || len < 0) 
    return("");
  else if (posit+len > s.length())
    return(s.substring(posit-1));
  else
    return(s.substring(posit-1,posit+len-1));
}


public static int mypos(String s1, String s2) 
{
  //if (s2 == "" && s1 != "")
  //if (s2.equals("") && (!s1.equals(""))
  if (s2.length() == 0 && s1.length() != 0)
    return(0);
  else
    return(s2.indexOf(s1) + 1);
}

public static String deblank(String s) 
{
  int i,posnb;

  posnb = -1;
  for (i=s.length()-1; i>=0; i--)
    { 
      if (!(s.substring(i,i+1).equals(" ")))
        posnb = i;
    }
  if (posnb != -1)
    return(s.substring(posnb,s.length()));   
  else
    return(s);
}



public static String shortenline(String line) 
{
    int posblank;

    posblank = mypos(" ", line);
    if (posblank==0) posblank = line.length() + 1;
    return(mycopy(line, posblank+1, line.length()));
} 




/*
  function to remove right blanks from line
*/

public static String trim(String line) /*string to be trimed*/
{
    int i,    /*pos in string*/
        nonbl;/*pos of rightmost non-blank*/

    nonbl = line.length();  /*in case no blanks in string, don't trim*/
    for (i = 1; i<= line.length(); i++ )
      {
        if (!(mycopy(line, i, 1).equals(" "))) nonbl = i;
      }
    return(mycopy(line, 1, nonbl));
} /*trim*/



public static boolean testAlls3(String s3, String s5, int k, int i)
{
  String sticker = alpha.substring(k-1-i,k-i);
  int newpos=s5.indexOf(sticker);
  int pos=-1;
  while (newpos>-1)
  {
    pos = pos + 1 + newpos;
    String oldsticker = s3.substring(pos,pos+1);
    if (oldsticker.equals(" "))
      return false;
    newpos = s5.substring(pos+1).indexOf(sticker);

   }
  return true;
}

public static int froms3(String s3, String s5, int k)
{
  int i;
  int x=0;
  for (i = 0; i<k; i++)
  { 
    if (testAlls3(s3,s5,k,i))
      x = x + (1<<i);
  }  
  return x;
}



public static String clearAlls3(String s3, String s5, int k, int i)
{
  String sticker = alpha.substring(k-1-i,k-i);
  int newpos=s5.indexOf(sticker);
  int pos=-1;
  while (newpos>-1)
  {
    pos = pos + 1 + newpos;
    String oldsticker = s3.substring(pos,pos+1);
    if (!(oldsticker.toUpperCase().equals(oldsticker)))  //clears stickers but not staples
      s3 = s3.substring(0,pos)+" "+s3.substring(pos+1);
    newpos = s5.substring(pos+1).indexOf(sticker);

   }
  return s3;
}


public static String setAlls3(String s3, String s5, int k, int i)
{
  String sticker = alpha.substring(k-1-i,k-i);
  int newpos=s5.indexOf(sticker);
  int pos=-1;
  while (newpos>-1)
  { 
    pos = pos + 1 + newpos;
    String oldsticker = s3.substring(pos,pos+1);
    //System.out.println("pos="+pos+" sticker="+sticker+" s3="+s3);
    if (oldsticker.equals(" "))
      s3 = s3.substring(0,pos)+sticker+s3.substring(pos+1);
    newpos = s5.substring(pos+1).indexOf(sticker);
   }
  //System.out.println("returned s3="+s3);
  return s3;
}

public static String setAlls3Staple(String s3, String s5, int k, int i)
{
  String sticker = alpha.substring(k-1-i,k-i);
  int newpos=s5.indexOf(sticker);
  int pos=-1;
  while (newpos>-1)
  {
    pos = pos + 1 + newpos;
    String oldsticker = s3.substring(pos,pos+1);
    if (oldsticker.equals(" "))
      s3 = s3.substring(0,pos)+sticker.toUpperCase()+s3.substring(pos+1);
    newpos = s5.substring(pos+1).indexOf(sticker);
   }
  return s3;
}


public static String tos3s5(int x, int k, String s5)
{ int i;
  String s3="                           ".substring(0,s5.length());
  for (i = k-1; i>=0; i--)
   { if ((x&(1<<i))!=0)
       s3 = setAlls3(s3,s5,k,i);
     //System.out.println("i="+i+" x="+x+" "+(x&(1<<i))+" k="+k+" s3="+s3);
   }
   //System.out.println("tos3s5="+s3);
   return s3;
 }


public static int getK()
 { return k-0; }
public static int getL()
 { return l-0; }
public static int getNumTubes()
 { return numTubes-0; }

public static int getNumStrands()
 { int ii,count=0;
   
   for (ii=0; ii < numStrands; ii++)
   { if ((tubeNum[ii] != -1)&&(s5[ii].length()!=0))
       count++;
   }
   return(count);
 }

public static int getTubePop(int i)
 { int ii; 
   int count=0;
   
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   return tubePop[i];
/*    
   else
   for (ii=0; ii < numStrands; ii++)
   { if ((tubeNum[ii] == i)&&(s5[ii].length()!=0))
       count++;
   }
   if (count != tubePop[i]) // 5/27/18
     System.out.println("ERROR tube " + i + " pop "+count+" vs "+tubePop[i]);
   return count;
*/
 }


public static String toBinary(int x, int k)
 { int i;
   String s="";
   int mask = 1<<k;
   for (i = k-1; i>=0; i--)
   { if ((x&(1<<i))!=0)
       s = s + "1";
     else
       s = s + "0";
     mask = mask >> 1;
   }
   return s;
 }

public static String toReverseBinary(int x, int k)
 { int i;
   String s="";
   int mask = 1<<k;
   for (i = k-1; i>=0; i--)
   { if ((x&(1<<i))!=0)
       s = "1" + s;
     else
       s = "0" + s;
     mask = mask >> 1;
   }
   return s;
 }


public static String getfirstfmt(String fmt) {
    int posd = mypos("d", fmt);
    if (posd==0) 
      posd = fmt.length();
    int posb = mypos("b", fmt);
    if (posb==0) 
      posb = fmt.length();
    int posr = mypos("r", fmt);
    if (posr==0) 
      posr = fmt.length();
    int posfmt = Math.min(Math.min(posd, posb), posr);
    return(mycopy(fmt, 1, posfmt));
  }


public static String formatConvert(int x, int k, String fmt)
{
  if ((k<=0)||(fmt.length()==0))
    return "";
  String firstfmt = getfirstfmt(fmt);
  //System.out.println("firstfmt="+firstfmt);
  Integer firstk = new Integer(mycopy(firstfmt, 1, firstfmt.length()-1));
  String firstx = ""+(x>>(k-firstk));
  if      (mycopy(firstfmt,firstfmt.length(),1).equals("b"))
    firstx = toBinary(new Integer(firstx),firstk);
  else if (mycopy(firstfmt,firstfmt.length(),1).equals("r"))
    firstx = toReverseBinary(new Integer(firstx),firstk);
  String restConvert = ""+formatConvert(x&((1<<(k-firstk))-1), k-firstk, mycopy(fmt, firstfmt.length()+1, fmt.length()));
  if (restConvert.length() == 0)
    return ""+firstx; 
  else
    return ""+firstx+"_"+restConvert;
}



public static String getTube(int i)
 { String s="";
   int ii;
   String convertedStrand;
   
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   else
   for (ii = 0; ii < numStrands; ii++)
   { if ((tubeNum[ii]==i)&&(s5[ii].length()!=0))
     { if (user_fmt.length() == 0)
         convertedStrand = "\""+s3[ii]+"\""; 
       else
         convertedStrand = formatConvert(froms3(s3[ii],s5[ii],k),k,user_fmt);
       if (s.length()==0)
         s = "" + convertedStrand;
       else
         s = s + "," + convertedStrand; 
     }
   }
   return s;
 }

 
public static String tubeToString()
 { int i;
   String s="";
   for (i=0; i < numTubes; i++)
     s = s + " " + i + ":{" + getTube(i) + "} ";
   return s;
 }

public static void listOn()
  { produce_listing = true; //form.list_button.value="List Off";
  }
public static void listOff()
  { produce_listing = false; //form.list_button.value="List On";
  }

public static void displayAfter()
 { 
   if (produce_listing)
     mywriteln(tubeToString());
 }

public static void display()
 {  
     mywriteln("    "+tubeToString());
 }

public static void init(int i)
 { int ii;
   if      (i>numTubes) 
     mywriteln("i too big");
   else if ((1<<l) > maxStrands-numStrands)
     myerr("exceed maxStrands");
   else 
     {
       for (ii=numStrands; ii < numStrands+(1<<l); ii++)
         { tubeNum[ii] = i;
           s5[ii] = alpha.substring(0,k); 
           s3[ii] = tos3s5(ii-numStrands,k,s5[ii]);
         }
       numStrands += 1<<l;
       tubePop[i] += 1<<l; //5/27/18
     }
   if (produce_listing)
     mywrite("init");
   displayAfter();
 }

public static void initCustom(int i, int v)
 { if      (i>numTubes) mywriteln("i too big");
   else if (v>=(1<<k))   mywriteln("v too big");
   else
     
     {
       tubeNum[numStrands] = i; 
       s5[numStrands] = alpha.substring(0,k);
       s3[numStrands] = tos3s5(v,k,s5[numStrands]);
       numStrands += 1;
       tubePop[i] += 1;  // 5/27/18
       if (numStrands >= maxStrands)
         myerr("exceed maxStrands");
     }
   if (produce_listing)
     mywrite("cust");
   displayAfter();
 }


public static void initCustomS5(int i, int v, String ss5)
 { if      (i>numTubes) mywriteln("i too big");
   else if (v>=(1<<k))   mywriteln("v too big");
   else if (ss5.length()>k) mywriteln("k too small");
   else
    
     {
       tubeNum[numStrands] = i; 
       s5[numStrands] = ss5;
       s3[numStrands] = tos3s5(v,k,ss5);
       numStrands += 1;
       tubePop[i] += 1;  // 5/27/18
       if (numStrands >= maxStrands)
         myerr("exceed maxStrands");
     }
   if (produce_listing)
     mywrite("custs5");
   displayAfter();
 }

public static void halfStaple(int i, int kk)
 { int ii;
   if (kk>=k)
     mywriteln("k too small");
   else
   
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == i)
       if (testAlls3(s3[ii],s5[ii],k,kk)==false)
         s3[ii] = setAlls3Staple(s3[ii],s5[ii],k,kk);
   }
   if (produce_listing)
     mywrite("halfstaple ");
   displayAfter();
 }

//this works with equal numbers in t0 and t1
//vaguely i+j i.e. t0+t1 (+ meaning concat)
// s5[ii].substring(s5[ii].length-1)== halfstaplei.toLowerCase()
// s5[jj].substring(0,1)            == halfstaplej.toLowerCase()
public static void staple1(int t0, int i, int j, int t1)
{ int ii,jj; 
  halfStaple(t0,i);
  
  {
   String halfstaplei = alpha.substring(k-1-i,k-i);
   String halfstaplej = alpha.substring(k-1-j,k-j);
   halfstaplei = halfstaplei.toUpperCase();
   halfstaplej = halfstaplej.toUpperCase();
   jj=0;
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == t0)
     {
       if (s3[ii].substring(s3[ii].length()-1).equals(halfstaplei))
       { for (jj=jj; jj < numStrands; jj++)
         { if (tubeNum[jj] == t1)
           {
             if (testAlls3(s3[jj],s5[jj],k,j)==false)
             { if (s5[jj].substring(0,1).toUpperCase().equals(
                   halfstaplej))
               { tubeNum[jj] = -1;
                 s3[ii] = s3[ii] + halfstaplej  + 
                          s3[jj].substring(1);
                 s5[ii] = s5[ii]+s5[jj];
                 break;
               }
             }
           }
         }
         if (tubeNum[jj]!=-1) 
            tubeNum[ii] = t1;
       }
     }
   }
   separate3(t1,t0,t1,j);
   if (produce_listing)
     mywrite("staple1 ");
   displayAfter();
  }
}

//j+i t1+t0
//  s5[ii].substring(0,1)            == halfstaplei.toLowerCase()
//  s5[jj].substring(s5[jj].length-1)== halfstaplej.toLowerCase()
public static void staple2(int t0, int i, int j, int t1)
{ int ii,jj; 
  halfStaple(t0,i);
  
  {
   String halfstaplei = alpha.substring(k-1-i,k-i);
   String halfstaplej = alpha.substring(k-1-j,k-j);
   halfstaplei = halfstaplei.toUpperCase();
   halfstaplej = halfstaplej.toUpperCase();
   //mywriteln(form,"i="+halfstaplei);
   //mywriteln(form,"j="+halfstaplej);

   jj=0;
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == t0)
     {
       if (s3[ii].substring(0,1).equals(halfstaplei))
       { for (jj=jj; jj < numStrands; jj++)
         { if (tubeNum[jj] == t1)
           {
             if (testAlls3(s3[jj],s5[jj],k,j)==false)
             { if (s5[jj].substring(s5[jj].length()-1).toUpperCase().equals(  //s5...len inside substr
                   halfstaplej))
               {tubeNum[jj] = -1;
                s3[ii] = s3[jj].substring(0,s3[jj].length()-1) 
                       +  halfstaplej  + s3[ii];
                s5[ii] = s5[jj] + s5[ii];
                break;
               }
             }
           }
         }
         if (tubeNum[jj]!=-1) 
            tubeNum[ii] = t1;
       }
     }
   }
   separate3(t1,t0,t1,j);
   if (produce_listing)
     mywrite("staple2 ");
   displayAfter();
  }
}

public static void staple(int t0, int i, int j, int t1)
{
  staple1(t0,i,j,t1);
  staple1(t0,j,i,t1);
  staple2(t0,i,j,t1);
  staple2(t0,j,i,t1);
}

//simple test of staple with k=8
public static void stapletest()
{

 System.out.println("Java Extended Stickers: stapletest");
 produce_listing = false;
 l = 0; 
 k = 8; 
 numTubes = 3;
 user_fmt = "";
 discard(0);  
 discard(1);
 discard(2);
 mywriteln("#1 == #2");
 initCustomS5(1,0<<2,"defa"); 
 initCustomS5(1,1<<2,"defa");
 initCustomS5(1,2<<2,"defa"); 
 initCustomS5(1,3<<2,"defa"); //sometimes comment out
 initCustomS5(2,0   ,"bghc"); 
 initCustomS5(2,1   ,"bghc");
 initCustomS5(2,2   ,"bghc"); 
 initCustomS5(2,3   ,"bghc"); //sometimes comment out
 staple(1,7,6,2);
 display();

 discard(1);
 discard(2);
 mywriteln("#1 < #2");
 initCustomS5(1,0<<2,"defa"); 
 initCustomS5(1,1<<2,"defa");
 initCustomS5(1,2<<2,"defa"); 
 //initCustomS5(1,3<<2,"defa");
 initCustomS5(2,0   ,"bghc"); 
 initCustomS5(2,1   ,"bghc");
 initCustomS5(2,2   ,"bghc"); 
 initCustomS5(2,3   ,"bghc"); //sometimes comment out
 staple(1,7,6,2);
 display();

 discard(1);
 discard(2);
 mywriteln("#1 > #2");
 initCustomS5(1,0<<2,"defa"); 
 initCustomS5(1,1<<2,"defa");
 initCustomS5(1,2<<2,"defa"); 
 initCustomS5(1,3<<2,"defa");
 initCustomS5(2,0   ,"bghc"); 
 initCustomS5(2,1   ,"bghc");
 initCustomS5(2,2   ,"bghc"); 
 //initCustomS5(2,3   ,"bghc"); //sometimes comment out
 staple(1,7,6,2);
 display();
}


public static void set(int i, int kk)
 { int ii;
   if (kk>=k)
     mywriteln("k too big");

  
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == i)
       s3[ii] = setAlls3(s3[ii],s5[ii],k,kk);
   }
   if (produce_listing)
     mywrite("set ");
   displayAfter();
 }

public static void clear(int i, int kk)
 { int ii;
   if (kk>=k)
     mywriteln("k too big");
   
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == i)
       s3[ii] = clearAlls3(s3[ii],s5[ii],k,kk);
   }
   if (produce_listing)
     mywrite("clr ");
   displayAfter();
 }

public static void separate(int i, int j, int k)
 { separate3(i, j, j, k); }

public static void separate3(int i1, int i0, int j, int kk)
 { int ii;
   if (kk>=k)
     mywriteln("k too big");
   
   if ((i1>=numTubes) || (i0>numTubes)|| (j>numTubes))
     mywriteln("tube "+i1+" or "+i0+" or "+j+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == j)
       if (testAlls3(s3[ii],s5[ii],k,kk))
         {tubeNum[ii] = i1; tubePop[j]--; tubePop[i1]++;} // 5/27/18
       else
         {tubeNum[ii] = i0; tubePop[j]--; tubePop[i0]++;} // 5/27/18
   }
   if (produce_listing)
     mywrite("sep ");
   displayAfter();
 }


public static void discard(int i)
 { int ii;
   
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == i)
       tubeNum[ii] = -1; 
   }
   tubePop[i] = 0; // 5/27/18
   if (produce_listing)
     mywrite("dis ");
   displayAfter();
 }


public static int removeOneStrand(int i)
 { 
   int ii,v=-1;
   
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   else
   {
     for (ii = 0; ii < numStrands; ii++)
     { if (tubeNum[ii]==i)
       { 
         v = froms3(s3[ii],s5[ii],k);
         tubeNum[ii] = -1;
         tubePop[i]--;  // 5/27/8
         break;
       }
     }
   }
   return v;
 }


public static void combine(int i, int j)
 { int ii;
   
   if ((i>=numTubes) || (j>numTubes))
     mywriteln("tube "+i+" or "+j+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == j)
       tubeNum[ii] = i;
   }
   tubePop[i] += tubePop[j];  // 5/27/18
   tubePop[j] = 0;
   if (produce_listing)
     mywrite("comb");
   displayAfter();
 }


public static void split(int i, int j)
 { int ii;
   
   if ((i>=numTubes) || (j>numTubes))
     mywriteln("tube "+i+" or "+j+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if ((tubeNum[ii] == j)&&(Math.random()>0.5))
       {tubeNum[ii] = i;  tubePop[j]--; tubePop[i]++;} //5/27/18
   }
   if (produce_listing)
     mywrite("split");
   displayAfter();
 }


public static void splitDeterministic(int i, int j)
 { int ii;
   boolean odd=false;
  
   if ((i>=numTubes) || (j>numTubes))
     mywriteln("tube "+i+" or "+j+" too big");
   else
   for (ii=0; ii < numStrands; ii++)
   { if (tubeNum[ii] == j)
     {  if (odd) {tubeNum[ii] = i; tubePop[j]--; tubePop[i]++;} //5/27/18
        odd = !odd;}
   }
   if (produce_listing)
     mywrite("splitDet");
   displayAfter();
 }

//****end of stickers with tubePop 5/27/18




public static void xortest()
{  int i;

   System.out.println("Java Extended Stickers: xor");
   produce_listing = true;
   l = 3; 
   k = 5; 
   numTubes = 4;
   user_fmt = "2d3d";
   discard(0);
   init(0); //tube0=k-bit strands;l bits init'ed;k-l bits=0\n";
   separate(1,0,0); //if bit0==0 stay tube0;else tube1\n";
   separate(3,1,1); //if bit1==0 stay tube1;else tube3\n";
   separate(2,0,1); //if bit1==0 stay tube0;else tube2\n";
   combine(1,2);   //pour tube 2 into 1\n";
   for (i=getL();i<=getL()+1;i++)//javascript loop example\n";
     set(1,i);                   //sets bits l,l+1 (ie2,3)\n";
   combine(0,3);   //pour tube 3 into 0\n";
   combine(0,1);   //pour tube 1 into 0\n";
   mywriteln("result: "+getTube(0));
   mywriteln("number: "+getTubePop(0));
}

/**********/

public static int getnN(int x)
{ int i,nN;
  nN=0;
  for (i=1; i<=aw-2; i++)  //don't do MSB
  { if ((x&(1<<i)) != 0)
    { if ((i+(((1<<i)-1)&x)) >= (1<<i) ) 
        nN = i;
    }
  }
  return nN;
}

public static void gennN(int start, int Mt, int Mts)
{int nN,iI;
 nN = getnN(start);
 //mywriteln(form,"start nN="+nN);
 for (iI = 1; iI<=(nN+1-(1<<nN)+((1<<nN)-1&start)); iI++)
 {//mywrite(form,start+" iI="+iI+" ");
  if (iI == 1)
    combine(Mts+nN,Mt+nN+1);
    //mywriteln(form,"combine("+(0+Mts+nN)+","+(1+nN)+")");
  else
    separate3(Mt+2+nN-iI,Mts+nN-iI+1,Mts+nN-iI+2,2+nN-iI);
    //mywriteln(form,"separate3("+(2+nN-iI)+","+
    //(0+Mts+nN-iI+1)+","+(0+Mts+nN-iI+2)+","+(2+nN-iI)+")");
 }
}


public static void printnN(int start, int Mt, int Mts)
{int nN,iI;
 nN = getnN(start);
 mywriteln("start nN="+nN);
 for (iI = 1; iI<=(nN+1-(1<<nN)+((1<<nN)-1&start)); iI++)
 {mywrite(start+" iI="+iI+" ");
  if (iI == 1)
    //combine(Mts+nN,nN+1);
    mywriteln("combine("+(0+Mts+nN)+","+(1+Mt+nN)+")");
  else
    //separate3(2+nN-iI,Mts+nN-iI+1,Mts+nN-iI+2,2+nN-iI);
    mywriteln("separate3("+(2+Mt+nN-iI)+","+
    (0+Mts+nN-iI+1)+","+(0+Mts+nN-iI+2)+","+(2+nN-iI)+")");
 }
}

public static void fetchpipe(int addr, int Mt, int Mts)
{int i;
 combine(Mts+aw-1,M);
 for (i=aw-1; i>1; i=i-1)
 {separate(temp,addr,i/*+aw*/);
  if (getTubePop(temp)>0)
    separate3(Mts+i-1,M,Mts+i,i);
  else
    separate3(Mt+i,Mts+i-1,Mts+i,i);
  combine(addr,temp);
 }

 separate(temp,addr,1/*+aw*/);  //maybe redund to extra iteration above
 if (getTubePop(temp)>0)
   separate3(Mts,M,Mts+1,1);
 else
   separate3(Mt+1,Mts,Mts+1,1);
 combine(addr,temp);
 
 separate(temp,addr,0/*+aw*/);
 if (getTubePop(temp)>0)
   separate3(Mt+0,M,Mts+0,0);
 combine(addr,temp);
}



public static void flipTubeState(boolean f, boolean s, int i)
{if (i==0)
 {
  if (s0) 
    {//mywrite(form,"S0 ");
     separate3(Mt+0,2*Mts,Mts+0,0);}
  else
    {//mywrite(form,"C0 ");
     combine(2*Mts,Mt+0);}
 }
 else
 {if (f)
  {if (s)
    {//mywrite(form,"S"+i+" ");
     separate3(Mt+i, Mts+i-1, Mts+i, i);}
   else
    {//mywrite(form,"C"+i+" ");
     combine(Mts+i-1,Mt+i);} 
  }
 }
}

public static void getCS(int Mt, int Mts)
{
 s0 = getTubePop(Mts+0)>0;
 s1 = getTubePop(Mts+1)>0;
 s2 = getTubePop(Mts+2)>0;
 S3 = getTubePop(Mts+3)>0;
 s4 = getTubePop(Mts+4)>0;
 S5 = getTubePop(Mts+5)>0;
 s6 = getTubePop(Mts+6)>0;
 s7 = getTubePop(Mts+7)>0;
 s8 = getTubePop(Mts+8)>0;
 s9 = getTubePop(Mts+9)>0;
 s10 = getTubePop(Mts+10)>0;
 s11 = getTubePop(Mts+11)>0;
 s12 = getTubePop(Mts+12)>0;
 s13 = getTubePop(Mts+13)>0;
 s14 = getTubePop(Mts+14)>0;
 s15 = getTubePop(Mts+15)>0;
 c0 = getTubePop(Mt+0)>0;
 c1 = getTubePop(Mt+1)>0;
 c2 = getTubePop(Mt+2)>0;
 c3 = getTubePop(Mt+3)>0;
 c4 = getTubePop(Mt+4)>0;
 c5 = getTubePop(Mt+5)>0;
 c6 = getTubePop(Mt+6)>0; 
 c7 = getTubePop(Mt+7)>0;
 c8 = getTubePop(Mt+8)>0;
 c9 = getTubePop(Mt+9)>0;
 c10 = getTubePop(Mt+10)>0;
 c11 = getTubePop(Mt+11)>0;
 c12 = getTubePop(Mt+12)>0;
 c13 = getTubePop(Mt+13)>0;
 c14 = getTubePop(Mt+14)>0;
 c15 = getTubePop(Mt+15)>0;
}


public static void iter()
{
 getCS(Mt, Mts);

 //mywriteln(form,"C0="+c0+"; C1="+c1+"; C2="+c2+"; C3="+c3+"; C4="+c4+";");
 //mywriteln(form,"S0="+s0+"; S1="+s1+"; S2="+s2+"; S3="+s3+"; S4="+s4+";");
 //mywriteln(form,"f1="+f1+"; f2="+f2+"; f3="+f3+";f4="+f4+";}");

 z1 = (!s1)&&(!c1);
 z2 = (!s2)&&(!c2);
 z3 = (!S3)&&(!c3);
 z4 = (!s4)&&(!c4);
 z5 = (!S5)&&(!c5); 
 z6 = (!s6)&&(!c6);
 z7 = (!s7)&&(!c7);
 z8 = (!s8)&&(!c8);
 z9 = (!s9)&&(!c9);
 z10 = (!s10)&&(!c10);
 z11 = (!s11)&&(!c11);
 z12 = (!s12)&&(!c12);
 z13 = (!s13)&&(!c13);
 z14 = (!s14)&&(!c14);

 f1 =  c0;
 f2 =  s0&&z1;
 f3 =  c0&&c1&&z2;
 f4 =  s0&&c1&&z2&&z3;
 f5 =  c0&&s1&&z2&&z3&&z4;
 f6 =  s0&&z1&&c2&&z3&&z4&&z5;
 f7 =  c0&&c1&&c2&&z3&&z4&&z5&&z6;
 f8 =  s0&&c1&&c2&&z3&&z4&&z5&&z6&&z7;
 f9 =  c0&&s1&&c2&&z3&&z4&&z5&&z6&&z7&&z8;
 f10 = s0&&z1&&s2&&z3&&z4&&z5&&z6&&z7&&z8&&z9;
 f11 = c0&&c1&&z2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10;
 f12 = s0&&c1&&z2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11;
 f13 = c0&&s1&&z2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12;
 f14 = s0&&z1&&c2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13;
 f15 = c0&&c1&&c2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14;

 flipTubeState(true,s0,0);
 flipTubeState(f1,s1,1);
 flipTubeState(f2,s2,2);
 flipTubeState(f3,S3,3);
 flipTubeState(f4,s4,4);
 flipTubeState(f5,S5,5);
 flipTubeState(f6,s6,6);
 flipTubeState(f7,s7,7);
 flipTubeState(f8,s8,8);
 flipTubeState(f9,s9,9);
 flipTubeState(f10,s10,10);
 flipTubeState(f11,s11,11);
 flipTubeState(f12,s12,12);
 flipTubeState(f13,s13,13);
 flipTubeState(f14,s14,14);
 flipTubeState(f15,s15,15);
}


public static void nletest()
{int i,jj,iii,start;
 System.out.println("Java Extended Stickers: NLE");
 produce_listing = false;
 user_fmt = "16d";
 aw = 16;
 l = aw; 
 k = aw; 
 numTubes = 2*aw+4;

 Mt = 0;
 Mts = aw;
 M = 2*aw+2;
 temp = 2*aw+1;
 PCIR = 2*aw+3;

 /*f1=false;
 f2=false;
 f3=false;
 f4=false;
 f5=false;
 f6=false; 
 f7=false;
 */

 init(Mts*2-1);

 for (start=0;start<(1<<aw); start++) 
 //for (start=0; start<=0;start++)
 //for (start=4085; start<=4085;start++)
 //for (start=8180; start<=8180;start++)
 //for (start=16371; start<=16371;start++)
 //for (start=32754; start<=32754;start++)
 {
  for(i=0;i<=M;i++)
    if (i != 2*Mts-1) combine(2*Mts-1, i);
  
  //for(i=0;i<=PCIR;i++)
  // discard(i);
  //init(Mts*2-1);
  discard(PCIR);
  initCustom(PCIR,start);
  fetchpipe(PCIR, Mt, Mts);
  printnN(start, Mt, Mts);
  gennN(start, Mt, Mts);
  //if ((start == 59)||(start==123)) combine(8,2);
   // 2^6-5=59, 2^10-9=1015, 2^11-10=2038,  2^(aw-1)-aw+2
   // 2^13-12=8180(no), 2^14-13=16371(yes), 2^15-14=32754 (no)
  if ((start&0x3f)==59)   combine(Mts+2-1, Mt+2);            //aw>=7
  if ((start&0x3ff)==1015) separate3(Mt+2,Mts+2-1,Mt+2+1,2); //aw>=11
  if ((start&0x7ff)==2038) combine(Mts+3-1, Mt+3);           //aw>=12
  if ((start&0x3fff)==16371) combine(Mts+2-1, Mt+2);         //aw>=15
   // if ((start&0x1fffff)==1048557) combine(Mts+4-1, Mt+4); //aw>=21
   // aw>=38??
  //if ((start>=249)&&(start<=504)) combine(18,9); //bug now fixed
  for(jj=start;jj<(1<<aw);jj++)
  {//mywrite(form,"if (start=="+jj+") {");
   //mywriteln(form,"ideal "+jj+" ");
   //if (jj==1015) {mywriteln("before");display();}
   //if (jj==16371) {mywriteln("before");display();}
   iter();
   //if (jj==1015) {mywriteln("after");display();}
  }
  for(iii=0;iii<2*aw;iii++)
  { if (getTubePop(iii)>0)
     mywrite("WRONG ");
  }
  mywriteln(""+start);
  //display();
 }
}

/*********/

public static void main(String [] arg)
 { 
   //xortest();
   nletest();
 }

}

