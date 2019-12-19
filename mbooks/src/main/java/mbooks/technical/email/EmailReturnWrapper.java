package mbooks.technical.email;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
public @Data class EmailReturnWrapper {
    @NonNull String email;
    @NonNull String title;
    @NonNull String endDate;
    @NonNull String returnDate;
}
