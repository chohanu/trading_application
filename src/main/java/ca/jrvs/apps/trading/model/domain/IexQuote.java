package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "askPrice",
        "askSize",
        "bidPrice",
        "bidSize",
        "id",
        "lastPrice",
        "ticker"
})
public class IexQuote {

    @JsonProperty("askPrice")
    private Long askPrice;
    @JsonProperty("askSize")
    private Long askSize;
    @JsonProperty("bidPrice")
    private Long bidPrice;
    @JsonProperty("bidSize")
    private Long bidSize;
    @JsonProperty("id")
    private String id;
    @JsonProperty("lastPrice")
    private Long lastPrice;
    @JsonProperty("ticker")
    private String ticker;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("askPrice")
    public Long getAskPrice() {
        return askPrice;
    }

    @JsonProperty("askPrice")
    public void setAskPrice(Long askPrice) {
        this.askPrice = askPrice;
    }

    @JsonProperty("askSize")
    public Long getAskSize() {
        return askSize;
    }

    @JsonProperty("askSize")
    public void setAskSize(Long askSize) {
        this.askSize = askSize;
    }

    @JsonProperty("bidPrice")
    public Long getBidPrice() {
        return bidPrice;
    }

    @JsonProperty("bidPrice")
    public void setBidPrice(Long bidPrice) {
        this.bidPrice = bidPrice;
    }

    @JsonProperty("bidSize")
    public Long getBidSize() {
        return bidSize;
    }

    @JsonProperty("bidSize")
    public void setBidSize(Long bidSize) {
        this.bidSize = bidSize;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("lastPrice")
    public Long getLastPrice() {
        return lastPrice;
    }

    @JsonProperty("lastPrice")
    public void setLastPrice(Long lastPrice) {
        this.lastPrice = lastPrice;
    }

    @JsonProperty("ticker")
    public String getTicker() {
        return ticker;
    }

    @JsonProperty("ticker")
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }



