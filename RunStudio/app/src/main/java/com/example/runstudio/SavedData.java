package com.roflanRun.CulComf;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

public class SavedData {

    static final String APP_PACKEGE = "com.roflanRun.CulComf";
    static boolean IsRegister = false;
    static final String APP_PREFERENCES = "my_settings";
    static final String APP_PREFERENCE_REG = "Registration";
    static final String APP_PREFERENCE_GENDER = "Gender";
    static final String APP_PREFERENCE_WEIGHT = "Weight";
    static final String APP_PREFERENCE_PRONATION = "Pronation";
    static final String APP_PREFERENCE_REAM_WIDTH = "Ream Width";
    static String Gender = "";
    static float Weight = 0.0f;
    static int Pronacia = 0;
    static boolean IsPronacia = false;
    static int ReamWidth = 0;

    static final String PronationDescription = "Pronation is a method of setting the outer part of the foot when walking and running. It performs its functions in the field of natural and internal mechanisms.\n" +
            "\n" +
            "Type of choice - one of the main points when choosing sneakers for running. It is necessary to take into account this factor in order to compensate for the natural deficiencies and speed up the distribution of the load on the joints and ligaments in order to avoid injury.\n" +
            "\n" +
            "The simplest test by definition is the so-called “wet test”. For this to be imprinted, you need a few seconds on a sheet of thick paper. Type of pronation is interpreted depending on the received outlines of the foot.\n" +
            "\n" +
            "1. Hyperpronation - reducing emphasis on the inside. Mice are in a weakened and stretched state, so depreciation does not work. Hyperpronation is often accompanied by flat-footedness and, as a result, an increase in the load on the knees, hip joint, back and so on.\n" +
            "2. Hypopronation - the lack of deflection of the foot, excessive collapse of the foot on the outer part. This makes natural depreciation more difficult and increases the risk of injury from fractures.\n" +
            "3. Neutral pronation is the optimal distribution of the load from impact to the surface.";
    static final String ReamWidthDescription = "1. Place your foot on a piece of paper while seated.\n" +
            "Sit down with your back straight on a chair. Take a piece of paper big enough for your whole foot to fit on. Place your foot flat on the paper.\n" +
            "If you intend to wear socks with the shoe you're buying, wear those socks while measuring your foot. \n" +
            "2. Trace your foot.\n" +
            "With a pen or pencil, trace the outline of your foot. Keep the pen or pencil as close to your foot as possible. This will help ensure an accurate measurement.\n" +
            "You'll get the most accurate measurement if you have someone else trace your foot while you remain seated upright, but it's okay to do it yourself.\n" +
            "3. Repeat with the other foot.\n" +
            "After you finish measuring your first foot, do the exact same process with the second foot. Feet are usually slightly different in size, so you'll be picking shoes with your larger foot in mind.\n" +
            "4. Measure the width between the widest points of your foot.\n" +
            "Identify the areas on your feet with the largest width. Take a tape measure or ruler to measure both widths.\n" +
            "5. Subtract to find your shoe width.\n" +
            "The first measurements will not be entirely accurate. The pencil will have created a small amount of space, making your measurements slightly larger than your actual feet. To determine your most accurate foot width, subtract 5 millimeters or 1/5 of an inch from each measurement.\n" +
            "6. Find your size in the table and match your foot width with the indicated ones.  If it is smaller, then your foot is narrow.";

    static String Collection = "";
    static int BootId = 0;

    static List<Integer> ListOfBootIds = new ArrayList<>();
    static List<Integer> ListOfCollectionIds = new ArrayList<>();

    public List<Integer> GetIds (DatabaseHelper dbHelper){
        List<Integer> IdBootslistForYou = new ArrayList<>();
        int count = dbHelper.getBootsCount();
        for (int i = 1; i <= count; i++) {
            Boot boot = dbHelper.getBoot(i);

            int pronation = boot.getPronation();
            boolean IsPronation = false;
            int rw = boot.getRw();
            boolean IsRw = false;
            String gender = boot.getGender();
            boolean IsGender = false;
            int weight = (int) boot.getWeight();
            boolean IsWeight = false;
            String purpose = boot.getPurpose();

            switch (purpose) {
                case "Running":
                    String tempPron = Integer.toString(pronation);
                    int[] pronArr = new int[tempPron.length()];
                    for (int j = 0; j < tempPron.length(); j++) {
                        pronArr[j] = Integer.decode(tempPron.substring(j, j + 1));
                    }
                    for (int j = 0; j < pronArr.length; j++) {
                        if (pronArr[j] == SavedData.Pronacia) {
                            IsPronation = true;
                        }
                    }

                    String tempRw = Integer.toString(rw);
                    int[] rwArr = new int[tempRw.length()];
                    for (int j = 0; j < tempRw.length(); j++) {
                        rwArr[j] = Integer.decode(tempRw.substring(j, j + 1));
                    }
                    for (int j = 0; j < rwArr.length; j++) {
                        if (rwArr[j] == SavedData.ReamWidth) {
                            IsRw = true;
                        }
                    }

                    IsWeight = true;

                    if(gender.equals(SavedData.Gender)){
                        IsGender = true;
                    }
                    break;

                case "Basketball":
                    IsPronation = true;

                    IsGender = true;

                    String tempRwbask = Integer.toString(rw);
                    int[] rwArrbask = new int[tempRwbask.length()];
                    for (int j = 0; j < tempRwbask.length(); j++) {
                        rwArrbask[j] = Integer.decode(tempRwbask.substring(j, j + 1));
                    }
                    for (int j = 0; j < rwArrbask.length; j++) {
                        if (rwArrbask[j] == SavedData.ReamWidth) {
                            IsRw = true;
                        }
                    }

                    String tempWeight = Integer.toString(weight);
                    int[] tempWeightArr = new int[tempWeight.length()];
                    for (int j = 0; j < tempWeight.length(); j++) {
                        tempWeightArr[j] = Integer.decode(tempWeight.substring(j, j + 1));
                    }
                    float[] arrWeight = new float[2];
                    switch (tempWeightArr[0]){
                        case 1:
                            arrWeight[0] = 0.0f;
                            switch (tempWeightArr[1]){
                                case 2:
                                    arrWeight[1] = 10.0f * tempWeightArr[2] + tempWeightArr[3];
                                    break;
                                case 3:
                                    arrWeight[1] = 100.0f * tempWeightArr[2] + 10.0f * tempWeightArr[3] + tempWeightArr[4];
                                    break;
                            }
                            break;
                        case 2:
                            int padd1 = tempWeightArr[1];
                            switch (padd1){
                                case 2:
                                    arrWeight[0] = 10.0f * tempWeightArr[2] + tempWeightArr[3];
                                    break;
                                case 3:
                                    arrWeight[0] = 100.0f * tempWeightArr[2] + 10.0f * tempWeightArr[3] + tempWeightArr[4];
                                    break;
                            }
                            switch (tempWeightArr[1 + padd1 + 1]){
                                case 2:
                                    arrWeight[1] = 10.0f * tempWeightArr[2 + padd1 + 1] + tempWeightArr[3 + padd1 + 1];
                                    break;
                                case 3:
                                    arrWeight[1] = 100.0f * tempWeightArr[2 + padd1 + 1] + 10.0f * tempWeightArr[3 + padd1 + 1] + tempWeightArr[4 + padd1 + 1];
                                    break;
                            }
                            break;
                        case 3:
                            arrWeight[1] = 300.0f;
                            switch (tempWeightArr[1]){
                                case 2:
                                    arrWeight[0] = 10.0f * tempWeightArr[2] + tempWeightArr[3];
                                    break;
                                case 3:
                                    arrWeight[0] = 100.0f * tempWeightArr[2] + 10.0f * tempWeightArr[3] + tempWeightArr[4];
                                    break;
                            }
                            break;
                    }
                    if (SavedData.Weight >= arrWeight[0] && SavedData.Weight <= arrWeight[1]){
                        IsWeight = true;
                    }
                    break;
            }
            if (IsGender && IsPronation && IsRw && IsWeight){
                IdBootslistForYou.add(boot.get_id());
            }
        }
        return IdBootslistForYou;
    }

}
