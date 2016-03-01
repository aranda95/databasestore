'use strict';

angular.module('databasestoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('azulejo', {
                parent: 'entity',
                url: '/azulejos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.azulejo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/azulejo/azulejos.html',
                        controller: 'AzulejoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('azulejo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('azulejo.detail', {
                parent: 'entity',
                url: '/azulejo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.azulejo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/azulejo/azulejo-detail.html',
                        controller: 'AzulejoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('azulejo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Azulejo', function($stateParams, Azulejo) {
                        return Azulejo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('azulejo.new', {
                parent: 'azulejo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/azulejo/azulejo-dialog.html',
                        controller: 'AzulejoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    marca: null,
                                    m2: null,
                                    descripcion: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('azulejo', null, { reload: true });
                    }, function() {
                        $state.go('azulejo');
                    })
                }]
            })
            .state('azulejo.edit', {
                parent: 'azulejo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/azulejo/azulejo-dialog.html',
                        controller: 'AzulejoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Azulejo', function(Azulejo) {
                                return Azulejo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('azulejo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('azulejo.delete', {
                parent: 'azulejo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/azulejo/azulejo-delete-dialog.html',
                        controller: 'AzulejoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Azulejo', function(Azulejo) {
                                return Azulejo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('azulejo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
