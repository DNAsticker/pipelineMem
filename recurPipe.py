def H(t,k):
    r = set()
    for i in t:
        if (i&(1<<k)):
             r.add(i)
    return r
def L(t,k):
    r=set()
    for i in t:
        if (i&(1<<k))==0:
            r.add(i)
    return r
def f(x,k):
    return (x+k)%(1<<k) == 0;
def s(x,k):
    if k>aw:
        return set()
    elif x<=st:
        return Is(0,k,st,M)
    elif len(s(x-1,k+1)) != 0:
        return L(s(x-1,k+1), k+1)
    elif f(x-1, k+1):
        return c(x-1,k+1)
    else:
        return set()
def c(x,k):
    if k>aw:
        return set()
    elif x<=st:
        return Ic(0,k,st,M)
    elif len(s(x-1,k)) != 0:
        return H(s(x-1,k), k)
    elif not f(x-1, k):
        return c(x-1,k)
    else:
        return set()
def Is(x,k,st,M):
    if (x==0) and (k==0) and ((st&1)!=0):
        return set()
    elif k!=-x:
        return set()
    elif x == 1-aw:
        return M
    elif (st&(1<<(k+1)))==0:
        return L(Is(x-1,k+1,st,M),k+1)
    else:
        return H(Is(x-1,k+1,st,M),k+1)
def Ic(x,k,st,M):
    if (x==0) and (k==0) and ((st&1)!=0):
        return H(Is(0,0,st-1,M),0)
    elif (k<=-x) or (k>=aw):
        return set()
    elif ((x==1-k) or (k==0)) and ((st&(1<<k))==0):
        return H(Is(x-1,k,st,M),k)
    elif ((x==1-k) or (k==0)) and ((st&(1<<k))!=0):
        return set()
    else:
        return Ic(x-1,k,st,M)
#init aw and make M have addresses between 0 and 2**aw-1
#aw=3
#M={0,1,2,3,4,5,6,7}
#aw=4
#M={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}
aw=5
M={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}
#choose a start address
#powers of two work; certain other values don't (and need the ideas in appendix)
st=16
print("init")
for x in range(1-aw,1):
    for col in range(-1, aw):
        print(Is(x,col,st,M),end=" ")
        if col != -1:
          print(Ic(x,col,st,M),end="  ")
    print(" x",end="=") 
    print(x)
print("pipe")
for x in range(st, (1<<aw)+1):
    for col in range(-1, aw):
        print(s(x,col),end=" ")
        if col != -1:
          print(c(x,col),end="  ")
    print(" x",end="=") 
    print(x)


