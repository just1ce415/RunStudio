package com.roflanRun.CulComf;

import java.util.List;

public interface DatabaseHelperI {
    public Boot getBoot(int id);
    public List<Boot> getAllBoots();
    public int getBootsCount();
}
