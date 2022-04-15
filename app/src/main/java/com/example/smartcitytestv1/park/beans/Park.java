package com.example.smartcitytestv1.park.beans;

public class Park {

    private int id;
    private String parkName;        //停车场名称
    private String vacancy;         //空位
    private String address;         //地址
    private String priceCaps;       //封顶价格
    private String imgUrl;          //图片链接
    private String rates;           //价格
    private String distance;        //距离
    private String allPark;         //所有车位
    private String open;            //是否开放

    public Park(int id, String parkName, String vacancy, String address, String priceCaps, String imgUrl, String rates, String distance, String allPark, String open) {
        this.id = id;
        this.parkName = parkName;
        this.vacancy = vacancy;
        this.address = address;
        this.priceCaps = priceCaps;
        this.imgUrl = imgUrl;
        this.rates = rates;
        this.distance = distance;
        this.allPark = allPark;
        this.open = open;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPriceCaps() {
        return priceCaps;
    }

    public void setPriceCaps(String priceCaps) {
        this.priceCaps = priceCaps;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAllPark() {
        return allPark;
    }

    public void setAllPark(String allPark) {
        this.allPark = allPark;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id=" + id +
                ", parkName='" + parkName + '\'' +
                ", vacancy='" + vacancy + '\'' +
                ", address='" + address + '\'' +
                ", priceCaps='" + priceCaps + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", rates='" + rates + '\'' +
                ", distance='" + distance + '\'' +
                ", allPark='" + allPark + '\'' +
                ", open='" + open + '\'' +
                '}';
    }
}
