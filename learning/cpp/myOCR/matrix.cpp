#include "matrix.h"

using namespace MyOCR;

ImgMatrix::ImgMatrix(int width, int height, int channels) {
    this->width = width;
    this->height = height;
    this->channels = channels;
    this->data = new pixel[width*height*channels];
}

ImgMatrix::~ImgMatrix() {
    delete [] data;
}

int ImgMatrix::getChannels() {
    return this->channels;
}

pixel* ImgMatrix::getData() {
    return this->data;
}

int ImgMatrix::getHeight() {
    return this->height;
}

int ImgMatrix::getWidth() {
    return this->width;
}

pixel ImgMatrix::get(int i, int j) {
    return this->data[channels * (i * width + j)];
}

pixel &ImgMatrix::operator()(int i, int j) {
    return data[channels * (i * width + j)];
}