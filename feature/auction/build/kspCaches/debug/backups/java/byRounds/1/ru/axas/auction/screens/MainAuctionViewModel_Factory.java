package ru.axas.auction.screens;

import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import ru.axas.auction.api.RealtyApi;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MainAuctionViewModel_Factory {
  private final Provider<RealtyApi> realtyApiProvider;

  public MainAuctionViewModel_Factory(Provider<RealtyApi> realtyApiProvider) {
    this.realtyApiProvider = realtyApiProvider;
  }

  public MainAuctionViewModel get() {
    return newInstance(realtyApiProvider.get());
  }

  public static MainAuctionViewModel_Factory create(Provider<RealtyApi> realtyApiProvider) {
    return new MainAuctionViewModel_Factory(realtyApiProvider);
  }

  public static MainAuctionViewModel newInstance(RealtyApi realtyApi) {
    return new MainAuctionViewModel(realtyApi);
  }
}
