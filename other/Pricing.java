package other;

public class Pricing {
    private double baseFee;
    private double vulnerable;
    private double device;
    private double general;

    public Pricing(double baseFee, double vulnerable, double device, double general){
        setBaseFee(baseFee);
        setVulnerable(vulnerable);
        setDevice(device);
        setGeneral(general);
    }
    
    // getter
    public double getBaseFee(){
        return baseFee;
    }
    public double getVulnerable(){
        return vulnerable;
    }
    public double getDevice(){
        return device;
    }
    public double getGeneral(){
        return general;
    }

    // setter
    public void setVulnerable(double vulnerable){
        this.vulnerable = vulnerable;
    }
    public void setBaseFee(double baseFee){
        this.baseFee = baseFee;
    }
    public void setDevice(double device){
        this.device = device;
    }
    public void setGeneral(double general){
        this.general = general;
    }


    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Pricing [baseFee=" + baseFee + ", vulnerable=" + vulnerable + ", device=" + device + ", general="
                + general + "]";
    }

    
    
    
}
