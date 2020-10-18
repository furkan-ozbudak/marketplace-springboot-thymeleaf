package edu.mum.cs.onlinemarketplace.service.impl;

import edu.mum.cs.onlinemarketplace.domain.Ads;
import edu.mum.cs.onlinemarketplace.repository.AdsRepository;
import edu.mum.cs.onlinemarketplace.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AdsServiceImpl implements AdsService {

    @Autowired
    AdsRepository adsRepository;

    @Override
    public List<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    @Override
    public Ads getAdsById(Long id) {
        return adsRepository.findById(id).get();
    }

    @Override
    public void deleteAdsById(Long id) {
        adsRepository.deleteById(id);
    }

    @Override
    public Ads saveAds(Ads ads) {
        return adsRepository.save(ads);
    }
}
