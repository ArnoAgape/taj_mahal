package com.openclassrooms.tajmahal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;
import com.openclassrooms.tajmahal.ui.adapters.ReviewsAdapter;
import com.openclassrooms.tajmahal.ui.review.ReviewsViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance())
                    .commitNow();
            setupRecyclerView();
            setupViewModel();
        }

    }
    /**
     * Configure le RecyclerView pour afficher la liste des reviews.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewsAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * Configure le ViewModel et observe les changements dans la liste des reviews.
     */
    private void setupViewModel() {
        ReviewsViewModel viewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        viewModel.getReview().observe(this, this::updateReviewList);
    }

    /**
     * Met à jour la liste des reviews affichée dans l'adaptateur.
     *
     * @param reviews La liste mise à jour des reviews.
     */
    private void updateReviewList(List<Review> reviews) {
        adapter.submitList(reviews);
    }
}

