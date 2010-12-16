#include "transform.h"
#include <cstdio>


#define min(X,Y) ( ((X)<(Y)) ? (X) : (Y) )
#define max(X,Y) ( ((X)>(Y)) ? (X) : (Y) )

using namespace MyOCR;

Transform::Transform() {
    cvtWeight[0]=0.3f; cvtWeight[1]=0.59f; cvtWeight[2]=0.11f; cvtWeight[3]=0;
    wsize=40; kfac=0.3;
}

void Transform::toGrayScale(ImgMatrix* res, ImgMatrix* m) {
    assert(res != 0); assert(m != 0);
    int N = m->getWidth()*m->getHeight(), K=m->getChannels();
    float ans;
    assert(K >= 3); assert(res->getChannels() == 1);
    assert(N <= res->getWidth()*res->getHeight());
    pixel *dres = res->getData(), *dm = m->getData();
    for (int i=0; i<N; i++) {
        ans=0;
        for (int k=0; k<K; k++) {
            ans += (*dm)*cvtWeight[k];
            dm++;
        }
        *dres = (pixel)ans;
        dres++;
    }
}

void compIntegralImg(ImgMatrix *m, ocr_int64 *sum, ocr_int64 *sumsq) {
    ocr_int64 row, rowsq;
    ocr_int64 *sump, *sumsp;
    int im_w = m->getWidth(), im_h = m->getHeight();
    pixel *img = m->getData();

    row=0; rowsq=0; sump = sum; sumsp = sumsq;
    for (int i=0; i<im_h; i++) {
        row += (*img); rowsq += (*img)*(*img); //row +=img[i][0]
        *sump = row; //sum[i][0] = row
        *sumsp = rowsq;
        img += im_w; sump += im_w; sumsp += im_w; //siguiente i
    }
    img = m->getData(); img++; sump = sum+1; sumsp = sumsq+1;
    for (int j=1; j<im_w; j++) {
        *sump = sump[-1] + (*img); //sum[0][j]=sum[0][j-1]+img[0][j];
        *sumsp = sumsp[-1] + (*img)*(*img);
        img++; sump++; sumsp++; //avanzar a la siguiente j
    }
    ocr_int64 *sumup=sum, *sumsup=sumsq;
    for (int i=1; i<im_h; i++) {
        row=(*img); rowsq=row*row; //row=img[i][0]
        img++; sump++; sumsp++; sumup++; sumsup++; //apuntar (j=1)
        for (int j=1; j<im_w; j++) {
            row += (*img); rowsq += (*img)*(*img); //row += img[i][j]
            *sump = *sumup + row; //sum[i][j] = sum[i-1][j] + row
            *sumsp = *sumsup + rowsq;
            img++;sump++;sumsp++;sumup++;sumsup++; //next j
        }
    }
}

void Transform::binarize(ImgMatrix* res, ImgMatrix* m) {
    int w = this->wsize;
    float k = this->kfac;
    assert(k >= 0.001 && k<=0.999);
    assert(w>0 && k<1000);
    assert(m != NULL);

    int xmin,ymin,xmax,ymax,area;
    int whalf = w >> 1;
    int im_w = m->getWidth(), im_h = m->getHeight();
    ocr_int64* sum = new ocr_int64[im_h*im_w];
    ocr_int64* sumsq = new ocr_int64[im_h*im_w];
    pixel *img, *ires;
    double mean, stddev, num, snum, thresh;    

    compIntegralImg(m,sum,sumsq);

    /*for (int i=0; i<5; i++) {
        for (int j=0; j<5; j++) {
            printf("%d,%d,%d\t",m->get(i,j),sum[i][j],sumsq[i][j]);
        }
        printf("\n");
    }*/

    img = m->getData(); ires = res->getData();
    for (int i=0; i<im_h; i++) {
        for (int j=0; j<im_w; j++) {
            xmin = max(0,j-whalf);
            ymin = max(0,i-whalf);
            xmax = min(im_w-1,j+whalf);
            ymax = min(im_h-1,i+whalf);
            area = (xmax-xmin+1)*(ymax-ymin+1);

            if (xmin==0 && ymin==0) {
                num = sum[ymax*im_w + xmax];
                snum = sumsq[ymax*im_w + xmax];
            } else if (xmin==0 && ymin>0) {
                num = sum[ymax*im_w + xmax] - sum[(ymin-1)*im_w + xmax];
                snum = sumsq[ymax*im_w + xmax] - sumsq[(ymin-1)*im_w + xmax];
            } else if (xmin>0 && ymin==0) {
                num = sum[ymax*im_w + xmax] - sum[ymax*im_w + (xmin-1)];
                snum = sumsq[ymax*im_w + xmax] - sumsq[ymax*im_w + (xmin-1)];
            } else {
                num = sum[ymax*im_w + xmax] + sum[(ymin-1)*im_w + (xmin-1)];
                num -= sum[ymax*im_w + (xmin-1)] + sum[(ymin-1)*im_w + xmax];
                snum = sumsq[ymax*im_w + xmax] + sumsq[(ymin-1)*im_w + (xmin-1)];
                snum -= sumsq[ymax*im_w + (xmin-1)] + sumsq[(ymin-1)*im_w + xmax];
            }
            mean = num/area;
            num = snum - (num*num/area);
            assert(num >= 0);
            stddev = sqrt( num / (area-1) );
            thresh = mean*(1+k*((stddev/128)-1));
            if (*img < thresh)
                *ires = 0;
            else
                *ires = 255;
            img++; ires++;
        }
    }
    delete [] sum; delete [] sumsq;
}
