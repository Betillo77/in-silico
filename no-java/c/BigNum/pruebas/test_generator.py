

import random

test_cases=100000
lower_bound=2**32
upper_bound=2**1024


for i in range(0,test_cases):
    a=random.randint(lower_bound,upper_bound)
    b=random.randint(lower_bound,2**1024)
    print "*",a,b


