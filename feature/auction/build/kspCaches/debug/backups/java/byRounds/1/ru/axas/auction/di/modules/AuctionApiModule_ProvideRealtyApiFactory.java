package ru.axas.auction.di.modules;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;
import ru.axas.auction.api.RealtyApi;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AuctionApiModule_ProvideRealtyApiFactory implements Factory<RealtyApi> {
  private final AuctionApiModule module;

  private final Provider<Retrofit> retrofitProvider;

  public AuctionApiModule_ProvideRealtyApiFactory(AuctionApiModule module,
      Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public RealtyApi get() {
    return provideRealtyApi(module, retrofitProvider.get());
  }

  public static AuctionApiModule_ProvideRealtyApiFactory create(AuctionApiModule module,
      Provider<Retrofit> retrofitProvider) {
    return new AuctionApiModule_ProvideRealtyApiFactory(module, retrofitProvider);
  }

  public static RealtyApi provideRealtyApi(AuctionApiModule instance, Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(instance.provideRealtyApi(retrofit));
  }
}
