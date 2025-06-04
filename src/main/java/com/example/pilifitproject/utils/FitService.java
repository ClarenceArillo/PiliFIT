package com.example.pilifitproject.utils;


import com.example.pilifitproject.dao.FitDAO;
import com.example.pilifitproject.model.Fit;
import java.sql.SQLException;

public class FitService {
        private final FitDAO fitDAO;

        public FitService() {
            this.fitDAO = new FitDAO();
        }

        public void saveGeneratedFit(String name, GeneratedFitPreview preview) throws SQLException {
            Fit fit = new Fit(
                    0, // auto-increment
                    name,
                    preview.getTop().getId(),
                    preview.getBottom().getId(),
                    preview.getShoes().getId(),
                    0 // not favorite by default
            );
            fitDAO.addFit(fit);
        }
    }


