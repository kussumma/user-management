package com.example.user_management.utility;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponseWrapper
 * Wrapper class for API responses
 * Contains message, code, meta, and data fields
 * Meta is used for paginated responses
 * Data is an Optional field to handle null data cases
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

    private String message;
    private int code;
    private Optional<Meta> meta;
    private Optional<T> data;

    /**
     * Constructor with message, code, and data to handle non-paginated responses
     * @param message Response message
     * @param code Response code
     * @param data Response data
     */
    public ResponseWrapper(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.meta = Optional.empty();
        this.data = Optional.of(data);
    }

    /**
     * Constructor with message, code, meta, and data to handle paginated responses
     * @param message Response message
     * @param code Response code
     * @param meta Response meta
     * @param data Response data
     */
    public ResponseWrapper(String message, int code, Meta meta, T data) {
        this.message = message;
        this.code = code;
        this.meta = Optional.of(meta);
        this.data = Optional.of(data);
    }

    /**
     * Constructor with message and code to handle responses without data
     * @param message Response message
     * @param code Response code
     */
    public ResponseWrapper(String message, int code) {
        this.message = message;
        this.code = code;
        this.meta = Optional.empty();
        this.data = Optional.empty();
    }

    /**
     * Meta
     * Contains metadata for paginated responses
     * Includes page, size, totalData, totalPage, prevPage, and nextPage fields
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {

        private int page;
        private int size;
        private int totalData;
        private int totalPage;
        private Optional<Integer> prevPage;
        private Optional<Integer> nextPage;

        /**
         * MetaBuilder
         * Builder class for Meta
         * Includes default values for prevPage and nextPage
         */
        public static MetaBuilder builder() {
            return new MetaBuilder()
                .prevPage(Optional.empty())
                .nextPage(Optional.empty());
        }
    }
}
