package edu.mum.cs.onlinemarketplace.service.impl;

import edu.mum.cs.onlinemarketplace.domain.Review;
import edu.mum.cs.onlinemarketplace.repository.ReviewRepository;
import edu.mum.cs.onlinemarketplace.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
   @Autowired
   private ReviewRepository reviewRepository;
      
    @Override
    public List<Review> getReviewsByProduct(Long id) { return reviewRepository.findReviewsById(id); }

    @Override
    public void addReview(Review review) { reviewRepository.save(review);}
    
    @Override
    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Long delete(Long id) {
         reviewRepository.deleteById(id);
         return id;
    }


    @Override
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).get();
    }

}
