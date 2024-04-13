package ru.axas.auction.screens;

import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainAuctionViewModel_Factory_Impl implements MainAuctionViewModel.Factory {
  private final MainAuctionViewModel_Factory delegateFactory;

  MainAuctionViewModel_Factory_Impl(MainAuctionViewModel_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public MainAuctionViewModel create() {
    return delegateFactory.get();
  }

  public static Provider<MainAuctionViewModel.Factory> create(
      MainAuctionViewModel_Factory delegateFactory) {
    return InstanceFactory.create(new MainAuctionViewModel_Factory_Impl(delegateFactory));
  }
}
