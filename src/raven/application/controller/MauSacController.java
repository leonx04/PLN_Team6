package raven.application.controller;

import java.util.List;
import raven.application.model.MauSacModel;
import raven.application.service.MauSacService;

public class MauSacController {
    private MauSacService mauSacService;

    public MauSacController() {
        this.mauSacService = new MauSacService();
    }

    public List<MauSacModel> getAllMauSac() {
        return mauSacService.getALLMauSac();
    }

    public List<MauSacModel> getIDByTenMS(String tenMau) {
        return mauSacService.getIDByTenMS(tenMau);
    }

    public int addMauSac(MauSacModel mauSac) {
        return mauSacService.insert(mauSac);
    }

    public String generateNewMauSacID() {
        return mauSacService.getNewIDMS();
    }

    public int updateMauSac(MauSacModel mauSac, String ma) {
        return mauSacService.update(mauSac, ma);
    }

    public int deleteMauSac(String ma) {
        return mauSacService.delete(ma);
    }
}
