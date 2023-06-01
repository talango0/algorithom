package reflect;

/**
 * @author mayanwei
 * @date 2023-06-01.
 */
class Rectangle {
    private Double w;
    private Double h;
    private Double area;

    public Rectangle() {
    }

    public Rectangle(Double w, Double h) {
        this.w = w;
        this.h = h;
        this.area = w * h;
    }

    public Double getW() {
        return w;
    }

    public void setW(Double w) {
        this.w = w;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}