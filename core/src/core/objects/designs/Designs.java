package core.objects.designs;

public class Designs {
    public final ButtonDesigns buttonDesigns;
    public final SliderDesigns sliderDesigns;
    public final CheckBoxDesigns checkBoxDesigns;
    
    public Designs() {
        buttonDesigns = new ButtonDesigns();
        sliderDesigns = new SliderDesigns();
        checkBoxDesigns = new CheckBoxDesigns();
    }
}
