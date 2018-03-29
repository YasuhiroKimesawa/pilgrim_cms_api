import datasources.article.ArticleDataSource
import models.article.ArticleRepository
import scaldi.Module
import usecases.article.ArticleUseCase
import userinterface.user.UserController
import userinterfaces.article.ArticleController

class DiModule extends Module {
  //bind[DataSource] when Condition(isLocal) to new DataSourceLocal
  //bind[DataSource] when Condition(!isLocal) to new DataSourceRemote

  //bind[TodoRepository] to new TodoDataSource(inject[DataSource])
  //binding to new TodoUseCase(inject[TodoRepository])

  // repository
  bind[ArticleRepository] to new ArticleDataSource()

  // usecase
  binding to new ArticleUseCase(inject[ArticleRepository])

  // controller
  binding to new ArticleController(inject[ArticleUseCase])
  binding to new UserController
}
