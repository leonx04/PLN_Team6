package raven.application.controller;

import java.util.List;
import raven.application.model.KichCoModel;
import raven.application.service.KichCoService;

public class KichCoController {
    private KichCoService kichCoService;

    public KichCoController() {
        this.kichCoService = new KichCoService();
    }

    public List<KichCoModel> getAllKichCo() {
        return kichCoService.getALLKichCo();
    }

    public List<KichCoModel> getKichCoByTen(String tenKichCo) {
        return kichCoService.getIDByTenKC(tenKichCo);
    }

    public int addKichCo(KichCoModel kichCo) {
        return kichCoService.insert(kichCo);
    }

    public String generateNewKichCoID() {
        return kichCoService.getNewIDKC();
    }

    public int updateKichCo(KichCoModel kichCo, String ma) {
        return kichCoService.update(kichCo, ma);
    }

    public int deleteKichCo(String ma) {
        return kichCoService.delete(ma);
    }
}
