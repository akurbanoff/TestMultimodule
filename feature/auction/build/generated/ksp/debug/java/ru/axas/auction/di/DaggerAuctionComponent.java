package ru.axas.auction.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import ru.axas.api.NetworkModule;
import ru.axas.api.NetworkModule_HttpLoggingInterceptorFactory;
import ru.axas.api.NetworkModule_ProvideOkHttpClientFactory;
import ru.axas.api.NetworkModule_ProvideRetrofitFactory;
import ru.axas.auction.api.RealtyApi;
import ru.axas.auction.di.modules.AuctionApiModule;
import ru.axas.auction.di.modules.AuctionApiModule_ProvideRealtyApiFactory;
import ru.axas.auction.screens.MainAuctionViewModel;
import ru.axas.auction.screens.MainAuctionViewModel_Factory;
import ru.axas.auction.screens.MainAuctionViewModel_Factory_Impl;

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
public final class DaggerAuctionComponent {
  private DaggerAuctionComponent() {
  }

  public static AuctionComponent.Builder builder() {
    return new Builder();
  }

  public static AuctionComponent create() {
    return new Builder().build();
  }

  private static final class Builder implements AuctionComponent.Builder {
    @Override
    public AuctionComponent build() {
      return new AuctionComponentImpl(new NetworkModule(), new AuctionApiModule());
    }
  }

  private static final class AuctionComponentImpl implements AuctionComponent {
    private final AuctionComponentImpl auctionComponentImpl = this;

    private Provider<HttpLoggingInterceptor> httpLoggingInterceptorProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<RealtyApi> provideRealtyApiProvider;

    private MainAuctionViewModel_Factory mainAuctionViewModelProvider;

    private Provider<MainAuctionViewModel.Factory> factoryProvider;

    private AuctionComponentImpl(NetworkModule networkModuleParam,
        AuctionApiModule auctionApiModuleParam) {

      initialize(networkModuleParam, auctionApiModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final NetworkModule networkModuleParam,
        final AuctionApiModule auctionApiModuleParam) {
      this.httpLoggingInterceptorProvider = DoubleCheck.provider(NetworkModule_HttpLoggingInterceptorFactory.create(networkModuleParam));
      this.provideOkHttpClientProvider = DoubleCheck.provider(NetworkModule_ProvideOkHttpClientFactory.create(networkModuleParam, httpLoggingInterceptorProvider));
      this.provideRetrofitProvider = DoubleCheck.provider(NetworkModule_ProvideRetrofitFactory.create(networkModuleParam, provideOkHttpClientProvider));
      this.provideRealtyApiProvider = DoubleCheck.provider(AuctionApiModule_ProvideRealtyApiFactory.create(auctionApiModuleParam, provideRetrofitProvider));
      this.mainAuctionViewModelProvider = MainAuctionViewModel_Factory.create(provideRealtyApiProvider);
      this.factoryProvider = MainAuctionViewModel_Factory_Impl.create(mainAuctionViewModelProvider);
    }

    @Override
    public MainAuctionViewModel.Factory getAuctionViewModel() {
      return factoryProvider.get();
    }
  }
}
