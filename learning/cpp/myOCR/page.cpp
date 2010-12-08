#include "page.h"
#include "component.h"

using namespace MyOCR;

Page::Page(Matrix &grayScale) {
    list <ConComponent*> comp;
    DocumentLayout dl;
    dl.connectedComponents(comp, grayScale);
    components = new vector<ConComponent*> (comp.begin(), comp.end());
    size = components->size();
}

Page::~Page() {
    delete components;
}

int Page::lenght() {
    return size;
}

ConComponent &Page::operator[](int index) {
    return *components->at(index);
}

