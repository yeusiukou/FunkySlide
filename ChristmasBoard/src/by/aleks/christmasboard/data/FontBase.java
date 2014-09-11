package by.aleks.christmasboard.data;

public interface FontBase {
	
	public static final FontInfo MENU_BUTTON_FONT = new FontInfo ("font.ttf", (int) (70*GameData.scale));
	
	public static final FontInfo DIALOG_SCORE_FONT = new FontInfo ("font.ttf", (int) (105*GameData.scale));
	public static FontInfo DIALOG_HEADER_FONT = new FontInfo ("sylfaen.ttf", (int) (120*GameData.scale));
	public static FontInfo TAPTOCHANGE_FONT = new FontInfo ("sylfaen.ttf", (int) (36*GameData.scale));
	public static FontInfo DIALOG_BUTTON_FONT = new FontInfo("font.ttf", (int) (50*GameData.scale));
	
	public static FontInfo START1_FONT = new FontInfo("segoesc.ttf", (int) (112*GameData.scale));
	public static FontInfo START2_FONT = new FontInfo("segoesc.ttf", (int) (35*GameData.scale));
	public static FontInfo START3_FONT = new FontInfo("segoesc.ttf", (int) (50*GameData.scale));
	
	//public static FontInfo SCORE_FONT = new FontInfo("font.ttf", (int) (105*GameData.scale));
	public static FontInfo SCORE_FONT = DIALOG_SCORE_FONT;
	
	public static FontInfo BOMB_TIMER_FONT = new FontInfo("sylfaen.ttf", (int) (50*GameData.scale));
	
	public static FontInfo POPUP1_FONT = MENU_BUTTON_FONT;
	public static FontInfo POPUP2_FONT = DIALOG_SCORE_FONT;
	public static FontInfo POPUP3_FONT = new FontInfo("font.ttf", (int) (125*GameData.scale));
	
	public static FontInfo SCORE_SCREEN_FONT = new FontInfo("font.ttf", (int) (90*GameData.scale));

}
