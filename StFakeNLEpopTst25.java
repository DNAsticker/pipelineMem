import java.lang.Integer;

public class StFakeNLEpopTst25{
// Faking DNA Sticker System for Ghosal-Sakar; only whether contents present or not

//globals needed for NLE
//note:  capital S3 and S5 to avoid naming conflict with Stickx
static boolean s0,s1,s2,S3,s4,S5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24;
static boolean c0,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23,c24;
static boolean z1,z2,z3,z4,z5,z6,z7,z8,z9,z10,z11,z12,z13,z14,z15,z16,z17,z18,z19,z20,z21,z22,z23;
static boolean f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17,f18,f19,f20,f21,f22,f23,f24;
static int aw, Mt, Mts, M, temp, PCIR;
static int testTubes, testMt, testMts, testtemp, testPCIR, numbad;  //tst
static String cstate,cteststate; //tst

//****sticker modified to have tubePop (except staple) 5/27/18
//int current_read_pos = 0;
//boolean read_safely = true;
static boolean produce_listing = true;
static int l = 2; //form.user_l.value;
static int k = 4; //form.user_k.value;
static int numTubes = 4;//form.user_numTubes.value;
static String user_fmt = "2d1b1b";

//static String alpha = "abcdefghijklmnopqrstuvwxyz";
static int numStrands = 0;
static int maxStrands = 63000; //not really strands anymore
//static String[] s3 = new String[maxStrands]; 
//static String[] s5 = new String[maxStrands]; 
//static int[] strand = new int[maxStrands]; 
//static int[] tubeNum = new int[maxStrands];  
static int[] tubePop = new int[maxStrands]; // a 1 or 0



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



public static int getK()
 { return k-0; }
public static int getL()
 { return l-0; }
public static int getNumTubes()
 { return numTubes-0; }


public static int getNumStrands()
 { int ii,count=0;   
   return(1);
 }


public static int getTubePop(int i)
 { int ii; 
   int count=0;
   
   if (i>=numTubes)
     mywriteln("tube "+i+" too big");
   return tubePop[i];
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
   { if (getTubePop(i)>0)
       s = "1";
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
 { tubePop[i] = 1; //5/27/18
   if (produce_listing)
     mywrite("init");
   displayAfter();
 }


public static void initCustom(int i, int v)
 { tubePop[i] = 1;  // 5/27/18
   if (produce_listing)
     mywrite("cust");
   displayAfter();
 }


public static void set(int i, int kk)
 { if (produce_listing)
     mywrite("set ");
   displayAfter();
 }

public static void clear(int i, int kk)
 { if (produce_listing)
     mywrite("clr ");
   displayAfter();
 }

public static void separate(int i, int j, int k)
 { separate3(i, j, j, k); }


public static void separate3(int i1, int i0, int j, int kk)
 { tubePop[j]=0; tubePop[i0]=1; tubePop[i1]=1; // 5/27/18
   if (produce_listing)
     mywrite("sep ");
   displayAfter();
 }


public static void discard(int i)
 { tubePop[i] = 0; // 5/27/18
   if (produce_listing)
     mywrite("dis ");
   displayAfter();
 }

public static int removeOneStrand(int i)
 { 
   return 1;
 }


public static void combine(int i, int j)
 { tubePop[i] = 1;  // 5/27/18
   tubePop[j] = 0;
   if (produce_listing)
     mywrite("comb");
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

public static void fetchpipestart(int start, int Mt, int Mts) //tst
{int i;
 combine(Mts+aw-1,M);
 for (i=aw-1; i>1; i=i-1)
 {if (((1<<i)&start)==(1<<i))
    separate3(Mts+i-1,M,Mts+i,i);
  else
    separate3(Mt+i,Mts+i-1,Mts+i,i);
 }

 if (((1<<1)&start)==(1<<1))
   separate3(Mts,M,Mts+1,1);
 else
   separate3(Mt+1,Mts,Mts+1,1);
 
 if ((1&start)==1)
   separate3(Mt+0,M,Mts+0,0);
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
 s16 = getTubePop(Mts+16)>0;
 s17 = getTubePop(Mts+17)>0;
 s18 = getTubePop(Mts+18)>0;
 s19 = getTubePop(Mts+19)>0;
 s20 = getTubePop(Mts+20)>0;
 s21 = getTubePop(Mts+21)>0;
 s22 = getTubePop(Mts+22)>0;
 s23 = getTubePop(Mts+23)>0;
 s24 = getTubePop(Mts+24)>0;
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
 c16 = getTubePop(Mt+16)>0;
 c17 = getTubePop(Mt+17)>0;
 c18 = getTubePop(Mt+18)>0;
 c19 = getTubePop(Mt+19)>0;
 c20 = getTubePop(Mt+20)>0;
 c21 = getTubePop(Mt+21)>0;
 c22 = getTubePop(Mt+22)>0;
 c23 = getTubePop(Mt+23)>0;
 c24 = getTubePop(Mt+24)>0;
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
 z15 = (!s15)&&(!c15);
 z16 = (!s16)&&(!c16);
 z17 = (!s17)&&(!c17);
 z18 = (!s18)&&(!c18);
 z19 = (!s19)&&(!c19);
 z20 = (!s20)&&(!c20);
 z21 = (!s21)&&(!c21);
 z22 = (!s22)&&(!c22);
 z23 = (!s23)&&(!c23);

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
 f16 = s0&&c1&&c2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15;
 f17 = c0&&s1&&c2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16;
 f18 = s0&&z1&&s2&&c3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16&&z17;
 f19 = c0&&c1&&z2&&S3&&z4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16&&z17&&z18;
 f20 = s0&&c1&&z2&&z3&&c4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16&&z17&&z18&&z19;
 f21 = c0&&s1&&z2&&z3&&c4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16&&z17&&z18&&z19&&z20;
 f22 = s0&&z1&&c2&&z3&&c4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16&&z17&&z18&&z19&&z20&&z21;
 f23 = c0&&c1&&c2&&z3&&c4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16&&z17&&z18&&z19&&z20&&z21&&z22;
 f24 = s0&&c1&&c2&&z3&&c4&&z5&&z6&&z7&&z8&&z9&&z10&&z11&&z12&&z13&&z14&&z15&&z16&&z17&&z18&&z19&&z20&&z21&&z22&&z23;

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
 flipTubeState(f16,s16,16);
 flipTubeState(f17,s17,17);
 flipTubeState(f18,s18,18);
 flipTubeState(f19,s19,19);
 flipTubeState(f20,s20,20);
 flipTubeState(f21,s21,21);
 flipTubeState(f22,s22,22);
 flipTubeState(f23,s23,23);
 flipTubeState(f24,s24,24);
}

public static void startup(int start, int Mt, int Mts, int temp, int PCIR)  //tst 
{ int i;
  fetchpipestart(start, Mt, Mts);  
  //printnN(start, Mt, Mts);
  gennN(start, Mt, Mts);
   // 2^6-5=59, 2^10-9=1015, 2^11-10=2038,  2^(aw-1)-aw+2
   // 2^13-12=8180(no), 2^14-13=16371(yes), 2^15-14=32754 (no)
  if ((start&0x3f)==59)   combine(Mts+2-1, Mt+2);                //aw>=7
  if ((start&0x3ff)==1015) separate3(Mt+2,Mts+2-1,Mt+2+1,2);     //aw>=11
  if ((start&0x7ff)==2038) combine(Mts+3-1, Mt+3);               //aw>=12
  if ((start&0x3fff)==16371) combine(Mts+2-1, Mt+2);             //aw>=15
  if ((start&0x3ffff)==262127)
  { separate3(Mt+3,Mts+3,Mt+3+1,3);                              //aw>=19
    separate3(Mt+2,Mts+2-1,Mts+2+1,2);
  }     
  if ((start&0x7ffff)==524270) separate3(Mt+3,Mts+3-1,Mt+3+1,3); //aw>=20
  if ((start&0xfffff)==1048557) combine(Mts+4-1, Mt+4);          //aw>=21
  if ((start&0x3fffff)==4194283) combine(Mts+2-1, Mt+2);         //aw>=23
}


public static void nletest()
{int i,jj,iii,start;
 System.out.println("Java Extended Stickers: NLE");
 produce_listing = false;
 user_fmt = "25d";
 aw = 25;
 l = aw; 
 k = aw; 
 testTubes = 2*aw+4;      //tst
 numTubes = 2*testTubes; //tst

 Mt = 0;
 Mts = aw;
 M = 2*aw+2;
 temp = 2*aw+1;
 PCIR = 2*aw+3;

 testMt = Mt + testTubes;      //tst
 testMts = Mts + testTubes;    //tst
 testtemp = temp + testTubes;  //tst
 testPCIR = PCIR + testTubes;  //tst

 init(Mts*2-1);
 init(testTubes + Mts*2-1);  //tst
 
/*
 int testN = 20;
 int testVal = (1<<testN)-testN+1;
 System.out.println("testVal="+testVal);
 for (start=testVal-3; start<=testVal-3;start++)
*/
 //for (start=0;start<(1<<aw); start++) 
 for (start=0; start<=0;start++)
 //for (start=4085; start<=4085;start++)
 //for (start=8180; start<=8180;start++)
 //for (start=16371; start<=16371;start++)
 //for (start=32754; start<=32754;start++)
 {
  for(i=0;i<=M;i++)
    if (i != 2*Mts-1) combine(2*Mts-1, i);
  startup(start, Mt, Mts, temp, PCIR);
  printnN(start, Mt, Mts);
  
  for(jj=start;jj<(1<<aw);jj++)
  { //mywrite(form,"if (start=="+jj+") {");
    //mywriteln(form,"ideal "+jj+" ");
    //if (jj==248) {mywriteln("before");display();}
    getCS(Mt,Mts);
    cstate = "C:"+c0+c1+c2+c3+c4+c5+c6+c7+c8+c9+c10+c11+c12+c13+c14+c15+c16+c17+c18+c19+c20+c21+c22+c23+c24+" S:"+s0+s1+s2+S3+s4+S5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18+s19+s20+s21+s22+s23+s24;
    for(i=0;i<=M;i++)
      if (i != testTubes+2*Mts-1) combine(testTubes+2*Mts-1, testTubes+Mt+i);
    startup(jj, testMt, testMts, testtemp, testPCIR);
    getCS(testMt,testMts);
    cteststate = "C:"+c0+c1+c2+c3+c4+c5+c6+c7+c8+c9+c10+c11+c12+c13+c14+c15+c16+c17+c18+c19+c20+c21+c22+c23+c24+" S:"+s0+s1+s2+S3+s4+S5+s6+s7+s8+s9+s10+s11+s12+s13+s14+s15+s16+s17+s18+s19+s20+s21+s22+s23+s24;
    if (!cstate.equals(cteststate))
      {System.out.println("BAD"+jj);
       System.out.println(cstate);
       System.out.println(cteststate);
    System.out.println(getTube(0)+"; "+getTube(1)+"; "+getTube(2)+"; "+getTube(3)+"; "+getTube(4));
    System.out.println(getTube(Mts+0)+"; "+getTube(Mts+1)+"; "+getTube(Mts+2)+"; "+getTube(Mts+3)+"; "+getTube(Mts+4));
    System.out.println(getTube(testTubes+0)+"; "+getTube(testTubes+1)+"; "+getTube(testTubes+2)+"; "+getTube(testTubes+3)+"; "+getTube(testTubes+4));
       numbad++; 
      }
//else
    if ((jj&0x7ffff)==0)  System.out.println("OK"+jj);
    iter();
    //if (jj==248) {mywriteln("after");display();}
  }
  //Fake cannot tell WRONG
  //for(iii=0;iii<2*aw;iii++)
  //{ if (getTubePop(iii)>0)
  //   mywrite("WRONG ");
  //}
  //mywriteln(""+start);
  //display();
 }
 System.out.println("numbad="+numbad); //tst
}

/*********/

public static void main(String [] arg)
 { 
   //xortest();
   nletest();
 }

}

