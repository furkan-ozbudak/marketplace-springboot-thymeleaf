package edu.mum.cs.onlinemarketplace.service;

import edu.mum.cs.onlinemarketplace.domain.Ads;
import edu.mum.cs.onlinemarketplace.domain.User;

import java.util.List;

public interface AdsService {
    List<Ads> getAllAds();
    Ads getAdsById(Long id);
    void deleteAdsById(Long id);
    Ads saveAds(Ads ads);
}
