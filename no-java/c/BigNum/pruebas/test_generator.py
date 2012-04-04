

import random

test_cases=1000
lower_bound=2**32
upper_bound=2**1024

opcodes=['+','-','/','%','^']


for i in range(0,test_cases):
    op=opcodes[random.randint(0,len(opcodes)-1)]
    a=random.randint(lower_bound,upper_bound)
    b=random.randint(lower_bound,upper_bound)
    print op,a,b,
    if (op=='^'):
        c=random.randint(lower_bound,upper_bound*2)
        print c
    else:
        print


